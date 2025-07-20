package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.service.EncounterService;
import org.nomisng.service.HouseholdMemberService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/household-members")
@RequiredArgsConstructor
@Slf4j
public class HouseholdMemberResource {
    private static final String ENTITY_NAME = "household_member";
    private final HouseholdMemberService householdMemberService;
    private final EncounterService encounterService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMemberDTO>> getAllHouseholds(@RequestParam(required = false, defaultValue = "*") String search, @PageableDefault(100) Pageable pageable, @RequestParam(required = false, defaultValue = "0") Integer memberType) {
        Page<HouseholdMember> householdMembersPage = householdMemberService.getAllHouseholdMembersPage(search, memberType, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), householdMembersPage);
        return new ResponseEntity<>(householdMemberService.getAllHouseholdMembersFromPage(householdMembersPage), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<HouseholdMemberDTO> getHouseholdMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getHouseholdMemberById(id));
    }

    @GetMapping("/{id}/forms")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<FormDTO>> getFormsByHouseholdMemberById(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getFormsByHouseholdMemberById(id));
    }

    @GetMapping("/{id}/household")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<HouseholdDTO> getHouseholdByHouseholdMemberId(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getHouseholdByHouseholdMemberId(id));
    }

    @GetMapping("/{id}/encounters")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdMemberId(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getEncountersByHouseholdMemberId(id));
    }

    @GetMapping("/{id}/{formCode}/encounters")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<EncounterDTO>> getEncountersByHouseholdMemberIdAndFormCode(@PathVariable Long id, @PathVariable String formCode, @RequestParam(required = false, defaultValue = "*") String dateFrom, @RequestParam(required = false, defaultValue = "*") String dateTo, @PageableDefault(100) Pageable pageable) {
        Page<Encounter> encounterPage;
        if ((dateFrom != null && !dateFrom.equalsIgnoreCase("*")) && (dateTo != null && !dateTo.equalsIgnoreCase("*"))) {
            encounterPage = encounterService.getEncountersByHouseholdMemberIdAndFormCodeAndDateEncounter(id, formCode, dateFrom, dateTo, pageable);
        } else {
            encounterPage = encounterService.getEncountersByHouseholdMemberIdAndFormCode(id, formCode, pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), encounterPage);
        return new ResponseEntity<>(encounterService.getEncounterDTOFromPage(encounterPage), headers, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<HouseholdMember> saveHouseholdMember(@Valid @RequestBody HouseholdMemberDTO householdMemberDTO) {
        if (householdMemberDTO.getId() != null) {
            return ResponseEntity.ok(householdMemberService.updateHouseholdMember(householdMemberDTO.getId(), householdMemberDTO));
        }
        return ResponseEntity.ok(householdMemberService.saveHouseholdMember(householdMemberDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<HouseholdMember> update(@Valid @RequestBody HouseholdMemberDTO householdMemberDTO, @PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.updateHouseholdMember(id, householdMemberDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        householdMemberService.deleteHouseholdMember(id);
    }

    @GetMapping("/{householdId}/membersAboutToGraduate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMember>> getAllMembersAboutToGraduate(@PathVariable Long householdId) {
        return ResponseEntity.ok(householdMemberService.getAllMembersAboutToGraduate(householdId));
    }

    @GetMapping("/{id}/aboutToGraduate")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Boolean> getMemberAboutToGraduate(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getMemberAboutToGraduate(id));
    }

    @GetMapping("/{id}/getCaregivers")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<HouseholdMember>> getHouseholdMemberCaregivers(@PathVariable Long id) {
        return ResponseEntity.ok(householdMemberService.getHouseholdMemberCaregivers(id));
    }

    @GetMapping("/{ovcId}/{caregiverId}/reAssignCaregiver")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Optional<HouseholdMember>> reAssignCaregiver(@PathVariable Long ovcId, @PathVariable Long caregiverId) {
        return ResponseEntity.ok(householdMemberService.reAssignCaregiver(ovcId, caregiverId));
    }

}
