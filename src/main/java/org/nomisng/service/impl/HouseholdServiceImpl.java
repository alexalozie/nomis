package org.nomisng.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.*;
import org.nomisng.domain.mapper.EncounterMapper;
import org.nomisng.domain.mapper.HouseholdMapper;
import org.nomisng.domain.mapper.HouseholdMemberMapper;
import org.nomisng.domain.mapper.HouseholdMigrationMapper;
import org.nomisng.repository.EncounterRepository;
import org.nomisng.repository.HouseholdMemberRepository;
import org.nomisng.repository.HouseholdMigrationRepository;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.service.HouseholdService;
import org.nomisng.service.UserService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.IllegalTypeException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class HouseholdServiceImpl implements HouseholdService {
    public static final int HOUSEHOLD_MEMBER_TYPE = 1;
    public static final int ACTIVE = 1;
    public static final long FIRST_MEMBER = 1L;
    private final HouseholdRepository householdRepository;
    private final HouseholdMemberRepository householdMemberRepository;
    private final HouseholdMigrationRepository householdMigrationRepository;
    private final EncounterMapper encounterMapper;
    private final EncounterRepository encounterRepository;
    private final HouseholdMapper householdMapper;
    private final HouseholdMemberMapper householdMemberMapper;
    private final HouseholdMigrationMapper householdMigrationMapper;
    private static final int ACTIVE_HOUSEHOLD_ADDRESS = 1;
    private static final int INACTIVE_HOUSEHOLD_ADDRESS = 0;
    private final EncounterService encounterService;
    private final UserService userService;
    private final HouseholdMemberService householdMemberService;
    private static final String HH_ASSESSMENT_FORM_CODE = "5f451d7d-213c-4478-b700-69a262667b89";
    private static final String HH_CARE_SUPPORT_CHECKLIST = "3c126779-e6ef-42d4-881e-04b381df9723";
    private static final String CARE_SUPPORT_KEY = "care_support";
    private static final String HOUSEHOLD_ID = "householdId";
    private static final String HOUSEHOLD_MEMBER_ID = "householdMemberId";
    private static final int CAREGIVER_TYPE = 1;
    private static final int OVC_TYPE = 2;
    private static final int OTHER_TYPE = 3;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Boolean isUniqueIdAvailable(String uniqueId) {
        Optional<Household> household = householdRepository.findByUniqueId(uniqueId);
        if (household.isPresent()) {
            return true;
        }
        return false;
    }

    public Page<Household> getAllHouseholdsByPage(String search, Pageable pageable) {
        Long cboProjectId = this.getCurrentCboProjectId();
        if (search == null || search.equalsIgnoreCase("*")) {
            return householdRepository.findByCboProjectIdAndArchivedOrderByIdDesc(cboProjectId, UN_ARCHIVED, pageable);
        }
        search = "%" + search + "%";
        return householdRepository.findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(search, cboProjectId, UN_ARCHIVED, pageable);
    }

    public List<HouseholdDTO> getAllHouseholdsFromPage(Page<Household> householdPage) {
        return householdMapper.toHouseholdDTOS(householdPage.getContent().stream().map(household -> this.setHouseholdFlag(household)).collect(Collectors.toList()));
    }

    private Household setHouseholdFlag(Household household) {
        List<Flag> flags = new ArrayList<>();
        household.getHouseholdFlagsById().forEach(memberFlag -> {
            flags.add(memberFlag.getFlag());
        });
        household.setFlags(flags);
        return household;
    }

    @Transactional(readOnly = true)
    public Optional<Household> findByUniqueId(String uniqueId) {
        return householdRepository.findByUniqueId(uniqueId);
    }

    public Household saveHousehold(HouseholdDTO householdDTO) {
        return saveOrUpdateHousehold(null, true, householdDTO);
    }

    public HouseholdDTO getHouseholdById(Long id) {
        Household household = getHousehold(id);
        List<HouseholdMember> householdMembers =  //start with caregiver
                household.getHouseholdMembers().stream().sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType)).collect(Collectors.toList());
        List<HouseholdMigration> householdMigrations = household.getHouseholdMigrations();
        List<HouseholdMemberDTO> householdMemberDTOS = householdMemberMapper.toHouseholdMemberDTOS(householdMembers);
        List<HouseholdMigrationDTO> householdMigrationDTOS = householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);
        Long householdMemberId = householdMemberDTOS.stream().findFirst().get().getId();
        HouseholdDTO householdDTO = householdMapper.toHouseholdDTO(household, householdMemberDTOS, householdMigrationDTOS);
        Optional<Encounter> optionalEncounter = encounterRepository.findTopByFormCodeAndHouseholdMemberIdAndArchivedOrderByIdAsc(HH_ASSESSMENT_FORM_CODE, householdMemberId, UN_ARCHIVED);
        optionalEncounter.ifPresent(encounter -> {
            householdDTO.setAssessment(encounter.getFormData().stream().findFirst().get().getData());
        });
        return householdDTO;
    }

    public List<EncounterDTO> getEncounterByHouseholdId(Long householdId) {
        Household household = getHousehold(householdId);
        List<Encounter> encounters = household.getEncountersById().stream().filter(encounter -> encounter.getArchived() != null && encounter.getArchived() == UN_ARCHIVED).map(encounter -> encounterService.addFirstNameAndLastNameAndFormNameToEncounter(encounter)).sorted(Comparator.comparing(Encounter::getId).reversed()).collect(Collectors.toList());
        return encounterMapper.toEncounterDTOS(encounters);
    }

    public Household updateHousehold(Long id, HouseholdDTO householdDTO) {
        getHousehold(id);
        return saveOrUpdateHousehold(id, false, householdDTO);
    }

    public void deleteHousehold(Long id) {
        Household houseHold = getHousehold(id);
        houseHold.setArchived(ARCHIVED);
        householdRepository.save(houseHold);
    }

    private Household saveOrUpdateHousehold(Long id, Boolean firstTime, HouseholdDTO householdDTO) {
        List<FormData> formDataList = new ArrayList<>();
        Long currentCboProjectId = getCurrentCboProjectId();
        householdDTO.setWardId(householdDTO.getWard().getId());
        Household household = householdMapper.toHousehold(householdDTO);
        //For updates and saving
        if (id != null) {
            household.setId(id);
        }
        household.setArchived(UN_ARCHIVED);
        household.setCboProjectId(currentCboProjectId);
        household.setStatus(ACTIVE);
        household.setCboProjectId(currentCboProjectId);
        if (household.getSerialNumber() == null) {
            //getting max household in the ward
            Optional<Long> optionalLong = householdRepository.findMaxSerialNumber(household.getWardId());
            if (optionalLong.isPresent()) {
                household.setSerialNumber(optionalLong.get());
            }
        }
        //save household
        household = householdRepository.save(household);
        HouseholdMember householdMember = null;
        if (householdDTO.getHouseholdMigrationDTOS() != null) {
            if (firstTime && householdDTO.getHouseholdMigrationDTOS().size() > 1) {
                throw new IllegalTypeException(HouseholdMigration.class, "HouseholdMigration", "should not be > 1 for registration");
            }
            List<HouseholdMigration> householdMigrations = householdMigrationMapper.toHouseholdMigration(householdDTO.getHouseholdMigrationDTOS());
            for (HouseholdMigration householdMigration : householdMigrations) {
                if (householdMigration.getActive() == null) {
                    householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);
                }//only one address at registration of household
                householdMigration.setHouseholdId(household.getId());
                householdMigrationRepository.save(householdMigration);
            }
        }
        if (householdDTO.getHouseholdMemberDTO() != null) {
            householdMember = householdMemberMapper.toHouseholdMember(householdDTO.getHouseholdMemberDTO());
            if (firstTime) {
                householdMember.setUniqueId(household.getUniqueId() + "/1"); //First household member
            }
            householdMember.setCboProjectId(currentCboProjectId);
            householdMember.setHouseholdId(household.getId());
            householdMember.setHouseholdMemberType(HOUSEHOLD_MEMBER_TYPE);
            householdMemberRepository.save(householdMember);
        }
        if (firstTime) {
            //Encounter for assessment
            EncounterDTO encounterDTO = new EncounterDTO();
            encounterDTO.setDateEncounter(LocalDateTime.now());
            encounterDTO.setFormCode(HH_ASSESSMENT_FORM_CODE);
            encounterDTO.setHouseholdId(household.getId());
            //FormData for assessment
            FormData formData = new FormData();
            formData.setData(householdDTO.getAssessment());
            formData.setArchived(UN_ARCHIVED);
            formData.setCboProjectId(currentCboProjectId);
            formDataList.add(formData);
            //set the formData in encounter
            encounterDTO.setFormData(formDataList);
            //save encounter
            encounterService.save(encounterDTO);
        }
        return household;
    }

    /*private Household update(Long id, HouseholdDTO householdDTO){
        Household household = householdRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id+""));

        return household;
    }*/
    public List<HouseholdMemberDTO> getHouseholdMembersByHouseholdId(Long id, Integer memberType) {
        Household household = getHousehold(id);
        //return every member
        if (memberType == null || memberType == 0) {
            return householdMemberMapper.toHouseholdMemberDTOS(//sort by memberType
                    //setting flag of household flag
                    household.getHouseholdMembers().stream().sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType)).map(householdMember -> householdMemberService.addMemberFlag(householdMember)).collect(Collectors.toList()));
        }
        //return specified memberType
        return householdMemberMapper.toHouseholdMemberDTOS(//sort by memberType
                //setting flag of household flag
                household.getHouseholdMembers().stream().filter(householdMember -> householdMember.getHouseholdMemberType() == memberType).sorted(Comparator.comparingInt(HouseholdMember::getHouseholdMemberType)).map(householdMember -> householdMemberService.addMemberFlag(householdMember)).collect(Collectors.toList()));
    }

    public List<HouseholdMigrationDTO> getHouseholdAddressesByHouseholdId(Long id) {
        Household household = getHousehold(id);
        List<HouseholdMigration> householdMigrations = //sort by id in descending/reversed order
                household.getHouseholdMigrations().stream().sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed()).collect(Collectors.toList());
        return householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);
    }

    public List<HouseholdMigrationDTO> saveHouseholdMigration(Long id, HouseholdMigration householdMigration) {
        getHousehold(id);
        //Deactivating the old address
        List<HouseholdMigration> householdMigrations = householdMigrationRepository.findAllByHouseholdIdAndActive(id, ACTIVE_HOUSEHOLD_ADDRESS).stream().map(householdMigration1 -> {
            householdMigration1.setActive(INACTIVE_HOUSEHOLD_ADDRESS);
            return householdMigration1;
        }).collect(Collectors.toList());
        migrateHouseholdMembers(id, householdMigration);
        householdMigration.setActive(ACTIVE_HOUSEHOLD_ADDRESS);
        householdMigration.setHouseholdId(id);
        // householdMigration.setMigrationType(1L); //1- migrated, 2- died, etc
        // householdMigration.setCboProjectId(userService.getUserWithRoles().get().getCurrentCboProjectId());
        householdMigrations.add(householdMigration);
        householdMigrations = //sort by id in descending/reversed order
                householdMigrationRepository.saveAll(householdMigrations).stream().sorted(Comparator.comparingLong(HouseholdMigration::getId).reversed()).collect(Collectors.toList());
        return householdMigrationMapper.toHouseholdMigrationDTOS(householdMigrations);
    }

    private Long getCurrentCboProjectId() {
        Long currentCboId = userService.getUserWithRoles().get().getCurrentCboProjectId();
        return currentCboId;
    }

    private Household getHousehold(Long id) {
        return householdRepository.findByIdAndCboProjectIdAndArchived(id, getCurrentCboProjectId(), UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Household.class, "Id", id + ""));
    }

    public Long getMaxHouseholdIdByWardId(Long wardId) {
        Optional<Long> optionalLong = householdRepository.findMaxSerialNumber(wardId);
        if (optionalLong.isPresent()) {
            return optionalLong.get();
        }
        return 0L;
    }

    public List<Household> getAllHouseholdAboutToGraduate() {
        return new ArrayList<>();
    }

    public List<Household> getHouseholdAboutToGraduate(Long householdId) {
        int numberOfChildren = getNumberOfChildrenInHH(householdId);
        Optional<Household> household = householdRepository.findById(householdId);
        List<Household> graduatedList = new ArrayList<>();
        if (household.isPresent()) {
            List<HouseholdMember> memberList = getHouseholdMembersAboutToGraduate(household.get().getId());
            if (memberList.size() >= numberOfChildren) {
                graduatedList.add(household.get());
            }
        }
        return graduatedList;
    }

    private int getNumberOfChildrenInHH(Long householdId) {
        try {
            int numberOfChildren = jdbcTemplate.queryForObject("select cast(details->>\'noOfChildren\' as int) as numChild from household" + " where id = " + householdId, Integer.class);
            return numberOfChildren;
        } catch (DataAccessException exception) {
            return 0;
        }
    }

    public List<HouseholdMember> getHouseholdMembersAboutToGraduate(Long householdId) {
        List<HouseholdMember> householdMemberList = householdMemberRepository.findByHouseholdIdAndArchived(householdId, UN_ARCHIVED);
        List<HouseholdMember> graduatedList = new ArrayList<>();
        householdMemberList.forEach(member -> {
            Optional<HouseholdMember> optMember = householdMemberRepository.findByIdAndArchived(member.getId(), UN_ARCHIVED);
            boolean status = false;
            if (optMember.isPresent()) {
                HouseholdMember hhMember = optMember.get();
                int age = getCurrentAge(hhMember.getHouseholdId(), hhMember.getId());
                boolean education = getEducationalStatus(hhMember.getHouseholdId(), member.getId());
                boolean hivPositive = getHivPositive(hhMember.getHouseholdId(), member.getId());
                String offered = getServiceOffered(hhMember.getHouseholdId(), member.getId());
                String provided = getServiceProvided(hhMember.getHouseholdId(), member.getId());
                if (provided.equalsIgnoreCase(offered)) {
                    if (education == false && age > 17 && hivPositive == false) {
                        status = true;
                    } else if (education == false && age > 21 && hivPositive) {
                        status = true;
                    }
                } else {
                    if (age > 17) {
                        if (age >= 21 && (education || hivPositive)) {
                            status = true;
                        } else {
                            status = !education && !hivPositive;
                        }
                    }
                }
            }
            if (status) {
                graduatedList.add(member);
            }

        });

        return graduatedList;
    }

    private Integer getCurrentAge(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT (DATE_PART(\'YEAR\', CURRENT_DATE) - " + "DATE_PART(\'YEAR\',TO_DATE(details->>\'dob\', \'YYYY-m-d\'))) current_age " + "FROM household_member WHERE id = :householdMemberId " + "AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Integer> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getInt(1));
        if (strLst.isEmpty()) {
            return 0;
        } else {
            return strLst.get(0);
        }
    }

    private String getServiceOffered(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT encounter.form_code, d->>\'services\' as service " +
                "FROM encounter join form_data  on encounter.id = form_data.encounter_id, " +
                " jsonb_array_elements(data->\'carePlan\') with ordinality a(c), " +
                " jsonb_array_elements(c->\'identifiedIssues\') with ordinality b(d) " +
                "where encounter.form_code = \'c4666b04-9357-4229-8683-de5efed78ab7\' " +
                "AND encounter.household_member_id = :householdMemberId AND " +
                "encounter.household_id = :householdId AND encounter.archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<String> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getString(1));
        if (strLst.isEmpty()) {
            return null;
        } else {
            return strLst.get(0);
        }
    }

    private String getServiceProvided(Long householdId, Long householdMemberId) {
        String service = null;
        try {
            String sqlStatement = "select service from ( select household_id, coalesce(count(e.household_id), 0) total, " +
                    "string_agg(c->>\'name\', \', \') service " +
                    "from encounter e join form_data f on e.id = f.encounter_id, " +
                    "jsonb_array_elements(data->\'serviceOffered\') with ordinality a(c) " +
                    "where f.data ?? \'serviceDate\' AND e.archived = 0 AND encounter.household_member_id = :householdMemberId " +
                    "and f.data->>\'serviceOffered\'  is not null group by household_id) as tbl " +
                    "where household_id = :householdId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
            params.addValue(HOUSEHOLD_ID, householdId);
            service = namedParameterJdbcTemplate.queryForObject(sqlStatement, params, String.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return service;
    }

    private Integer getServiceProvided(Long householdId) {
        Integer total = 0;
        try {
            String sqlStatement = "select total from ( select household_id, coalesce(count(e.household_id), 0) total, string_agg(c->>\'name\', \', \') service " +
                    "from encounter e join form_data f on e.id = f.encounter_id, " +
                    "jsonb_array_elements(data->\'serviceOffered\') with ordinality a(c) " +
                    "where f.data ?? \'serviceDate\' AND e.archived = 0 " +
                    "and f.data->>\'serviceOffered\' is not null group by household_id) as tbl " +
                    "where household_id = :householdId";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue(HOUSEHOLD_ID, householdId);
            total = namedParameterJdbcTemplate.queryForObject(sqlStatement, params, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
        }
        return total;
    }

    private Boolean getEducationalStatus(Long householdId, Long householdMemberId) {
        //check if child is in school, true:yes, false:no
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sqlStatement = "SELECT case when f.data->\'childInSchool\'->>\'code\' " + "= \'51dc4de7-8c88-4963-b255-331bf930b7c0\' THEN true " + "ELSE false END status FROM encounter e join form_data f on e.id = f.encounter_id " + "WHERE e.form_code = \'f4f553f3-46eb-4968-b895-3a2397a80cb3\'  " + "AND e.household_id =:householdId AND e.archived = 0";
        if (householdMemberId == -1L) {
            sqlStatement = "SELECT case when f.data->\'childInSchool\'->>\'code\' " + "= \'51dc4de7-8c88-4963-b255-331bf930b7c0\' THEN true " + "ELSE false END status FROM encounter e join form_data f on e.id = f.encounter_id " + "WHERE e.form_code = \'f4f553f3-46eb-4968-b895-3a2397a80cb3\'  " + "AND e.household_member_id = :householdMemberId AND e.household_id =:householdId AND e.archived = 0";
            params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        }
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private Boolean getCaregiverHivPositive(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT case when details->\'caregiver\'->\'details\'->\'hivStatus\'->>\'code\' " + "= \'e528faaf-7735-4cbc-a1b4-d110247b7227\' THEN true " + "ELSE false END status FROM household_member " + "WHERE id = :householdMemberId AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private Boolean getHivPositive(Long householdId, Long householdMemberId) {
        String sqlStatement = "SELECT CASE  WHEN details->\'hivStatus\'->>\'code\' = \'e528faaf-7735-4cbc-a1b4-d110247b7227\' " + "THEN true ELSE false END AS status FROM household_member " + "WHERE id = :householdMemberId AND household_id =:householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        params.addValue(HOUSEHOLD_ID, householdId);
        List<Boolean> strLst = namedParameterJdbcTemplate.query(sqlStatement, params, (rs, rowNum) -> rs.getBoolean(1));
        return !strLst.isEmpty();
    }

    private List<Map<String, Object>> getHouseholdMembersAbove17(Long householdMemberId) {
        String sqlStatement = "SELECT id, unique_id, TO_DATE(details->>\'dob\', \'YYYY-m-d\') dob, " + "(DATE_PART(\'YEAR\', CURRENT_DATE) - " + "DATE_PART(\'YEAR\',TO_DATE(details->>\'dob\', \'YYYY-m-d\'))) current_age " + "FROM household_member WHERE (DATE_PART(\'YEAR\', CURRENT_DATE) - " + "DATE_PART(\'YEAR\',TO_DATE(details->>\'dob\', \'YYYY-m-d\')))  > 18 " + "AND id = :householdMemberId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(HOUSEHOLD_MEMBER_ID, householdMemberId);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            map.put("unique_id", StringUtils.trimToEmpty("unique_id"));
            map.put("dob", StringUtils.trimToEmpty("dob"));
            map.put("current_age", StringUtils.trimToEmpty("current_age"));
            mapList.add(map);
        });
        return mapList;
    }

    public Optional<HouseholdMigration> getMigratedStatus(Long householdId) {
        return Optional.ofNullable(householdMigrationRepository.findAllByHouseholdIdAndActive(householdId, UN_ARCHIVED).stream().filter(res -> res.getMigrationType() > 0).max(Comparator.comparingLong(HouseholdMigration::getId)).orElseThrow(null));
    }

    public void migrateHouseholdMembers(Long householdId, HouseholdMigration householdMigration) {
        String newAddress = StringUtils.join(" ", StringUtils.trimToEmpty(householdMigration.getStreet()), StringUtils.trimToEmpty(householdMigration.getLandmark()), StringUtils.trimToEmpty(householdMigration.getCity()));
        String sqlStatement = "UPDATE household_member set details = jsonb_set(details::jsonb, \'{address}\', \':address::text\', false) " + "where household_id = :householdId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("address", newAddress).addValue(HOUSEHOLD_ID, householdId);
        int response = namedParameterJdbcTemplate.update(sqlStatement, params);
    }

    public List<Map<String, Object>> getServiceDueDate(Long householdId) {
        String sqlStatement = "SELECT encounter_id, jsonb_extract_path_text(k,\'services\') services, " +
                "k->>\'dateFollowUp\' dateFollowUp, " + "jsonb_extract_path_text(l,\'identifiedIssues\') issues  " +
                "from form_data join encounter e on form_data.encounter_id = e.id, " +
                "jsonb_array_elements(data->\'carePlan\') with ordinality a(l), " +
                "jsonb_array_elements(l->\'identifiedIssues\') with ordinality b(k) " +
                "where form_data.data->>\'carePlan\' is not null  " +
                "and current_date >= TO_DATE(k->>\'dateFollowUp\', \'YYYY-m-d\') " +
                "and e.household_id = :householdId AND e.archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("typeId", 1).addValue(HOUSEHOLD_ID, householdId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            Map<String, Object> map = new HashMap<>();
            map.put("services", StringUtils.trimToEmpty(rs.getString("services")));
            map.put("timeFrame", StringUtils.trimToEmpty(rs.getString("timeFrame")));
            map.put("identifiedIssues", StringUtils.trimToEmpty(rs.getString("issues")));
            mapList.add(map);
        });
        return mapList;
    }

    public HouseholdMember convertOvcToCaregiver(Long householdId, Long householdMemberId) {
        Optional<HouseholdMember> householdMember = householdMemberRepository.findByIdAndArchived(householdMemberId, UN_ARCHIVED);
        if (householdMember.isPresent()) {
            HouseholdMember updateHouseholdMember = householdMember.get();
            updateHouseholdMember.setHouseholdMemberType(1); /// 1 - caregiver, 2 - ovc, 3 - others
            return householdMemberRepository.save(updateHouseholdMember);
        } else {
            throw new EntityNotFoundException(HouseholdMember.class, "Id", householdMemberId + "");
        }
    }

    public Optional<Household> reAssignPrimaryCaregiver(Long householdId, Long caregiverId) {
        AtomicLong memberId = new AtomicLong();
        String sqlStatement = "SELECT details " + "FROM household_member WHERE household_member_type = :typeId AND " + "id = :caregiverId AND archived = 0";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("typeId", CAREGIVER_TYPE).addValue("caregiverId", caregiverId);
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            if (rs.getString("details") != null) {
                try {
                    JSONObject jsonObject = new JSONObject(rs.getString("details"));
                    String query = "UPDATE household set details = jsonb_set(details::jsonb, \'{primaryCareGiver}\', to_jsonb(:caregiver::json), false) " + "where id = :householdId AND archived = 0";
                    MapSqlParameterSource param = new MapSqlParameterSource().addValue("caregiver", jsonObject.toString()).addValue(HOUSEHOLD_ID, householdId);
                    memberId.set(namedParameterJdbcTemplate.update(query, param));
                    log.info("id: {}", memberId.get());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        if (memberId.get() > 0) {
            return householdRepository.findByIdAndArchived(householdId, UN_ARCHIVED);
        }
        return Optional.empty();
    }

    public List<HouseholdMember> getHouseholdCaregivers(Long householdId) {
        return householdMemberRepository.findByHouseholdIdAndHouseholdMemberTypeAndArchived(householdId, CAREGIVER_TYPE, UN_ARCHIVED);
    }

    public Map<String, Object> getCareSupportChecklistByHouseholdId(Long householdId) {
        Map<String, Object> map = new HashMap<>();
        String sqlStatement = "SELECT encounter.id, fd.data as care_support FROM encounter JOIN form_data fd " +
                "ON encounter.id = fd.encounter_id  WHERE encounter.form_code = :formCode " +
                " and encounter.household_id = :householdId";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("formCode", HH_CARE_SUPPORT_CHECKLIST);
        params.addValue(HOUSEHOLD_ID, householdId);
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            ObjectMapper mapObject = new ObjectMapper();
            try {
                JsonNode rootNode = mapObject.readTree(rs.getObject(CARE_SUPPORT_KEY).toString());
                log.info("data {}", rootNode);
                map.put(CARE_SUPPORT_KEY, rootNode);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return map;
    }

    private String getValueFromJsonField(String details, String field) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(details).get(field);
            if (!tree.isNull()) {
                return String.valueOf(tree).replaceAll("^\"+|\"+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkCareplanService(String service, String domain, Long householdId, Long householdMemberId) {
        String sqlStatement = "  SELECT household_id,household_member_id, c->\'domain\'->>\'name\' domain_name, d->>\'services\' offered_service " +
                "    FROM encounter join form_data on encounter.id = form_data.encounter_id, " +
                "    jsonb_array_elements(data->\'carePlan\') with ordinality a(c), " +
                "    jsonb_array_elements(c->\'identifiedIssues\') with ordinality b(d) " +
                "    where encounter.form_code = \'c4666b04-9357-4229-8683-de5efed78ab7\'  and " +
                "    household_id = :householdId  and household_member_id = :householdMemberId ";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("householdId", householdId);
        params.addValue("householdMemberId", householdMemberId);
        AtomicBoolean status = new AtomicBoolean();
        namedParameterJdbcTemplate.query(sqlStatement, params, rs -> {
            if (rs.getString("offered_service") != null && rs.getString("domain_name") != null) {
                status.set(true);
            }
        });
        return false;
    }

    public Long getTotalHousehold(Long cboProjectId) {
        return householdRepository.getTotalHousehold(cboProjectId);
    }

    public Long getTotalGraduated(Long cboProjectId) {
        return householdRepository.getTotalGraduated(cboProjectId);
    }


}
