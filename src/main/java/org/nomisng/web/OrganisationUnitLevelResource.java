package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;
import org.nomisng.service.OrganisationUnitLevelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organisation-unit-levels")
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitLevelResource {
    private final OrganisationUnitLevelService organisationUnitLevelService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OrganisationUnitLevelDTO> save(@Valid @RequestBody OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        return ResponseEntity.ok(organisationUnitLevelService.saveOrgUnitLevel(organisationUnitLevelDTO));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OrganisationUnitLevelDTO> update(@PathVariable Long id, @Valid @RequestBody OrganisationUnitLevelDTO organisationUnitLevelDTO) {
        return ResponseEntity.ok(organisationUnitLevelService.updateOrgUnitLevel(id, organisationUnitLevelDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitLevelDTO>> getAllOrganizationUnitLevel(@RequestParam(required = false, defaultValue = "2") Integer status) {
        return ResponseEntity.ok(organisationUnitLevelService.getAllOrganizationUnitLevel(status));
    }

    @GetMapping("{id}/organisation-units")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getAllOrganisationUnitsByOrganizationUnitLevel(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitLevelService.getAllOrganisationUnitsByOrganizationUnitLevel(id));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OrganisationUnitLevelDTO> getOrganizationUnitLevel(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitLevelService.getOrganizationUnitLevel(id));
    }

}
