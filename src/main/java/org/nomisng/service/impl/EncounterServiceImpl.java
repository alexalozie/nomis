package org.nomisng.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.service.EncounterService;
import org.nomisng.service.FlagService;
import org.nomisng.service.vm.DataHelper;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.entity.FormFlag;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.EncounterRepository;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.repository.FormFlagRepository;
import org.nomisng.util.AccessRight;
import org.nomisng.util.JsonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EncounterServiceImpl implements EncounterService {
    private final EncounterRepository encounterRepository;
    private final FormDataRepository formDataRepository;
    private final FormFlagRepository formFlagRepository;
    private final FlagService flagService;
    private final EncounterMapper encounterMapper;
    private final FormDataMapper formDataMapper;
    private final AccessRight accessRight;
    private final DataHelper dataHelper;
    private static final String WRITE = "write";
    private static final String DELETE = "delete";
    private static final String READ = "read";
    private static final String DATE_FROM = "dateFrom";
    private static final String DATE_TO = "dateTo";
    private static final int ASSOCIATED_WITH = 0;

    @Override
    public List<EncounterDTO> getAllEncounters() {
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        List<Encounter> encounters = encounterRepository.findAllByCboProjectIdAndArchived(cboProjectId, UN_ARCHIVED);
        List<Encounter> encounterList = new ArrayList<>();
        encounters.forEach(singleEncounter -> {
            //filtering by user permission
            if (!accessRight.grantAccessForm(singleEncounter.getFormCode(), permissions)) {
                return;
            }
            encounterList.add(this.addFirstNameAndLastNameAndFormNameToEncounter(singleEncounter));
        });
        return encounterMapper.toEncounterDTOS(encounterList);
    }

    @Override
    public EncounterDTO getEncounterById(Long id) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        Encounter encounter = encounterRepository.findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id", id + ""));
        //Grant access
        accessRight.grantAccess(encounter.getFormCode(), Encounter.class, accessRight.getAllPermissionForCurrentUser());
        return encounterMapper.toEncounterDTO(encounter);
    }

    @Override
    public Encounter update(Long id, EncounterDTO encounterDTO) {
        accessRight.grantAccessByAccessType(encounterDTO.getFormCode(), Encounter.class, WRITE, this.checkForEncounterAndGetPermission());
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        encounterRepository.findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id", id + ""));
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setId(id);
        encounter.setArchived(UN_ARCHIVED);
        //Start of flag operation for associated with (0)
        this.flagOperationInEncounter(encounter.getId(), encounter.getFormCode(), encounter.getHouseholdId(), encounter.getHouseholdMemberId());
        return encounterRepository.save(encounter);
    }

    @Override
    public Encounter save(EncounterDTO encounterDTO) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        //Grant access by access type = WRITE
        accessRight.grantAccessByAccessType(encounterDTO.getFormCode(), Encounter.class, WRITE, accessRight.getAllPermissionForCurrentUser());
        Encounter encounter = encounterMapper.toEncounter(encounterDTO);
        encounter.setCboProjectId(cboProjectId);
        encounter.setArchived(UN_ARCHIVED);
        encounter = encounterRepository.save(encounter);
        final Long finalEncounterId = encounter.getId();
        encounterDTO.getFormData().forEach(formData -> {
            formData.setEncounterId(finalEncounterId);
            formData.setCboProjectId(cboProjectId);
            formData.setArchived(UN_ARCHIVED);
        });
        formDataRepository.saveAll(encounterDTO.getFormData());
        //Start of flag operation for associated with (0)
        this.flagOperationInEncounter(encounter.getId(), encounter.getFormCode(), encounter.getHouseholdId(), encounter.getHouseholdMemberId());
        return encounter;
    }


    private void flagOperationInEncounter(Long encounterId, String formCode, Long householdId, Long householdMemberId) {
        //Start of flag operation for associated with (0)
        List<FormFlag> formFlags = formFlagRepository.findByFormCodeAndStatusAndArchived(formCode, ASSOCIATED_WITH, UN_ARCHIVED);
        if (!formFlags.isEmpty()) {
            final Object finalFormData = formDataRepository.findOneByEncounterIdOrderByIdDesc(encounterId).get().getData();
            flagService.checkForAndSaveMemberFlag(householdId, householdMemberId, JsonUtil.getJsonNode(finalFormData), formFlags);
        }
    }

    @Override
    public void delete(Long id) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        Encounter encounter = encounterRepository.findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id", id + ""));
        accessRight.grantAccessByAccessType(encounter.getFormCode(), Encounter.class, DELETE, this.checkForEncounterAndGetPermission());
        List<FormData> formData = new ArrayList<>();
        encounter.setArchived(ARCHIVED);
        encounter.getFormData().forEach(formDatum -> {
            formDatum.setArchived(ARCHIVED);
            formData.add(formDatum);
        });
        //save all corresponding formData
        formDataRepository.saveAll(formData);
        encounterRepository.save(encounter);
    }

    @Override
    public List<FormDataDTO> getFormDataByEncounterId(Long encounterId) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        Encounter encounter = encounterRepository.findByIdAndCboProjectIdAndArchived(encounterId, cboProjectId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Encounter.class, "Id", encounterId + ""));
        accessRight.grantAccessByAccessType(encounter.getFormCode(), Encounter.class, READ, this.checkForEncounterAndGetPermission());
        return formDataMapper.toFormDataDTOS(encounter.getFormData());
    }

    @Override
    public Page<Encounter> getEncountersByHouseholdMemberIdAndFormCode(Long householdMemberId, String formCode, Pageable pageable) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, this.checkForEncounterAndGetPermission());
        return encounterRepository.findAllByHouseholdMemberIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(householdMemberId, formCode, cboProjectId, UN_ARCHIVED, pageable);
    }

    @Override
    public Page<Encounter> getEncountersByHouseholdIdAndFormCode(Long householdId, String formCode, Pageable pageable) {
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, this.checkForEncounterAndGetPermission());
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        return encounterRepository.findAllByHouseholdIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(householdId, formCode, cboProjectId, UN_ARCHIVED, pageable);
    }

    @Override
    public List<EncounterDTO> getEncounterDTOFromPage(Page<Encounter> encounterPage) {
        List<Encounter> encounters = encounterPage.getContent().stream().map(this::addFirstNameAndLastNameAndFormNameToEncounter).collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    @Override
    public List<FormDataDTO> getFormDataDTOFromPage(Page<Encounter> encounterPage) {
        List<FormData> formData = encounterPage.getContent().stream().map(Encounter::getFormData).flatMap(Collection::stream).collect(Collectors.toList());
        return formDataMapper.toFormDataDTOS(formData);
    }

    @Override
    public Page<Encounter> getEncounterByHouseholdIdAndFormCodeAndDateEncounter(Long householdId, String formCode, String dateFrom, String dateTo, Pageable pageable) {
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, this.checkForEncounterAndGetPermission());
        HashMap<String, LocalDate> localDateHashMap = this.getDates(dateFrom, dateTo);
        LocalDate localDateFrom = localDateHashMap.get(DATE_FROM);
        LocalDate localDateTo = localDateHashMap.get(DATE_TO);
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        return encounterRepository.findAllByHouseholdIdAndFormCodeAndArchivedAndCboProjectIdAndDateEncounterOrderByIdDesc(householdId, formCode, UN_ARCHIVED, cboProjectId, localDateTo, localDateFrom, pageable);
    }

    @Override
    public Page<Encounter> getEncountersByHouseholdMemberIdAndFormCodeAndDateEncounter(Long householdMemberId, String formCode, String dateFrom, String dateTo, Pageable pageable) {
        accessRight.grantAccessByAccessType(formCode, Encounter.class, READ, this.checkForEncounterAndGetPermission());
        HashMap<String, LocalDate> localDateHashMap = this.getDates(dateFrom, dateTo);
        LocalDate localDateFrom = localDateHashMap.get(DATE_FROM);
        LocalDate localDateTo = localDateHashMap.get(DATE_TO);
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        return encounterRepository.findAllByHouseholdMemberIdAndFormCodeAndArchivedAndCboProjectIdAndDateEncounterOrderByIdDesc(householdMemberId, formCode, UN_ARCHIVED, cboProjectId, localDateTo, localDateFrom, pageable);
    }

    private HashMap<String, LocalDate> getDates(String dateFrom, String dateTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        HashMap<String, LocalDate> localDateHashMap = new HashMap<>();
        LocalDate localDateFrom = LocalDate.now();
        LocalDate localDateTo;
        if (!StringUtils.isBlank(dateFrom) && !dateFrom.equalsIgnoreCase("*")) {
            localDateFrom = LocalDate.parse(dateFrom, formatter);
        }
        if (!StringUtils.isBlank(dateTo) && !dateTo.equalsIgnoreCase("*")) {
            localDateTo = LocalDate.parse(dateTo, formatter);
        } else {
            localDateTo = localDateFrom;
        }
        localDateHashMap.put(DATE_FROM, localDateFrom);
        localDateHashMap.put(DATE_TO, localDateTo);
        return localDateHashMap;
    }

    public Encounter addFirstNameAndLastNameAndFormNameToEncounter(Encounter encounter) {
        String nameRegex = "^\"+|\"+$";
        encounter.setFormName(encounter.getFormByFormCode().getName());
        if (encounter.getHouseholdMemberByHouseholdMemberId() != null) {
            HouseholdMember householdMember = encounter.getHouseholdMemberByHouseholdMemberId();
            String firstName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "firstName").replaceAll(nameRegex, "");
            String lastName = JsonUtil.traverse(JsonUtil.getJsonNode(householdMember.getDetails()), "lastName").replaceAll(nameRegex, "");
            encounter.setFirstName(firstName);
            encounter.setLastName(lastName);
        }
        return encounter;
    }

    private Set<String> checkForEncounterAndGetPermission() {
        return accessRight.getAllPermissionForCurrentUser();
    }

}
