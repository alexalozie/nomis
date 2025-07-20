package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.*;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.entity.HouseholdMigration;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdService;
import org.nomisng.util.PaginationUtil;
import org.nomisng.web.apierror.BadRequestAlertException;
import org.nomisng.web.apierror.RecordExistException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
@Slf4j
public class HouseholdResource {
    private static final String ENTITY_NAME = "households";
    private final HouseholdService householdService;
    private final EncounterService encounterService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdDTO>> getAllHouseholds(@RequestParam(required = false, defaultValue = "*") String search, @PageableDefault(100) Pageable pageable) {
        Page<Household> householdPage = householdService.getAllHouseholdsByPage(search, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), householdPage);
        return new ResponseEntity<>(householdService.getAllHouseholdsFromPage(householdPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/encounters")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdId(@PathVariable Long id) {
        if (id == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return ResponseEntity.ok(householdService.getEncounterByHouseholdId(id));
    }

    @GetMapping("/{id}/{formCode}/encounters")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdIdAndFormCode(@PathVariable Long id, @PathVariable String formCode, @RequestParam(required = false, defaultValue = "*") String dateFrom, @RequestParam(required = false, defaultValue = "*") String dateTo, @PageableDefault(100) Pageable pageable) {
        Page<Encounter> encounterPage;
        if ((dateFrom != null && !dateFrom.equalsIgnoreCase("*")) && (dateTo != null && !dateTo.equalsIgnoreCase("*"))) {
            encounterPage = encounterService.getEncounterByHouseholdIdAndFormCodeAndDateEncounter(id, formCode, dateFrom, dateTo, pageable);
        } else {
            encounterPage = encounterService.getEncountersByHouseholdIdAndFormCode(id, formCode, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getEncounterDTOFromPage(encounterPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}/{formCode}/formData")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<FormDataDTO>> getFormDataByHouseholdIdAndFormCode(@PathVariable Long id, @PathVariable String formCode, @PageableDefault(100) Pageable pageable) {
        Page<Encounter> encounterPage = encounterService.getEncountersByHouseholdIdAndFormCode(id, formCode, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getFormDataDTOFromPage(encounterPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<HouseholdDTO> getHouseholdById(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdById(id));
    }

    @GetMapping("/{id}/householdMembers")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMemberDTO>> getHouseholdMembersByHouseholdId(@PathVariable Long id, @RequestParam(required = false, defaultValue = "0") Integer memberType) {
        return ResponseEntity.ok(householdService.getHouseholdMembersByHouseholdId(id, memberType));
    }

    @GetMapping("/{id}/householdAddress")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMigrationDTO>> getHouseholdAddressesByHouseholdId(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdAddressesByHouseholdId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Household> saveHousehold(@RequestBody @Valid HouseholdDTO householdDTO) {
        if (householdService.isUniqueIdAvailable(householdDTO.getUniqueId())) {
            throw new RecordExistException(Household.class, "Unique Id already exist");
        }
        return ResponseEntity.ok(householdService.saveHousehold(householdDTO));
    }

    @PostMapping("/{id}/householdMigration")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMigrationDTO>> saveHouseholdAddress(@PathVariable Long id, @Valid @RequestBody HouseholdMigration householdMigration) {
        return ResponseEntity.ok(householdService.saveHouseholdMigration(id, householdMigration));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Household> update(@Valid @RequestBody HouseholdDTO householdDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdService.updateHousehold(id, householdDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        householdService.deleteHousehold(id);
    }

    @GetMapping("/{id}/getServiceDueDate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<Map<String, Object>>> getServiceDueDate(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getServiceDueDate(id));
    }

    @GetMapping("/{id}/getHouseholdCaregivers")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMember>> getHouseholdCaregivers(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdCaregivers(id));
    }

    @GetMapping("/{householdId}/{householdMemberId}/reAssignCaregiver")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Optional<Household>> reAssignPrimaryCaregiver(@PathVariable Long householdId, @PathVariable Long householdMemberId) {
        return ResponseEntity.ok(householdService.reAssignPrimaryCaregiver(householdId, householdMemberId));
    }

    @GetMapping("/{id}/getMigratedStatus")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Optional<HouseholdMigration>> getMigratedStatus(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getMigratedStatus(id));
    }

    @GetMapping("/{id}/aboutToGraduate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<Household>> getHouseholdsAboutToGraduate(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.getHouseholdAboutToGraduate(id));
    }

    @GetMapping("/{householdId}/membersAboutToGraduate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMember>> getAllMembersAboutToGraduate(@PathVariable Long householdId) {
        return ResponseEntity.ok(householdService.getHouseholdMembersAboutToGraduate(householdId));
    }

    @GetMapping("/check-status/{service}/{domain/{householdId}/{householdMemberId}")
    public void checkServiceStatus(@PathVariable String service, @PathVariable String domain, @PathVariable Long householdId,
                                   @PathVariable Long householdMemberId) {
        householdService.checkCareplanService(service, domain, householdId, householdMemberId);
    }

    @GetMapping("/total-household/{cboProjectId}")
    public Long totalHousehold(@PathVariable Long cboProjectId) {
        return householdService.getTotalHousehold(cboProjectId);
    }

    @GetMapping("/total-graduated/{cboProjectId}")
    public Long totalGraduated(@PathVariable Long cboProjectId) {
        return householdService.getTotalHousehold(cboProjectId);
    }
}
