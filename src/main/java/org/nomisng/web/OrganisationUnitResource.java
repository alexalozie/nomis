package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.service.OrganisationUnitService;
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

@RestController
@RequestMapping("/api/organisation-units")
@Slf4j
@RequiredArgsConstructor
public class OrganisationUnitResource {
    private final OrganisationUnitService organisationUnitService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> saveOrganisationUnit(@RequestParam Long parentOrganisationUnitId, @RequestParam Long organisationUnitLevelId, @Valid @RequestBody List<OrganisationUnitDTO> organisationUnitDTOS) {
        return ResponseEntity.ok(organisationUnitService.saveOrganisationUnit(parentOrganisationUnitId, organisationUnitLevelId, organisationUnitDTOS));
    }

    /*@PostMapping("/save-all")
    public ResponseEntity<List<OrganisationUnit>> saveAll(@RequestParam String path) {
        return ResponseEntity.ok(organisationUnitService.saveAll(path));
    }*/
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OrganisationUnitDTO> update(@PathVariable Long id, @Valid @RequestBody OrganisationUnitDTO organisationUnitDTO) {
        return ResponseEntity.ok(organisationUnitService.updateOrganisationUnit(id, organisationUnitDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OrganisationUnitDTO> getOrganisationUnitById(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitService.getOrganisationUnitById(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getAllOrganisationUnit() {
        return ResponseEntity.ok(organisationUnitService.getAllOrganisationUnit());
    }

    @GetMapping("/parent-org-unit/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getOrganisationUnitByParentOrganisationUnitId(@PathVariable Long id) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitByParentOrganisationUnitId(id));
    }

    @GetMapping("/{parentOrgUnitId}/{orgUnitLevelId}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(@PathVariable Long parentOrgUnitId, @PathVariable Long orgUnitLevelId) {
        return ResponseEntity.ok(this.organisationUnitService.getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(parentOrgUnitId, orgUnitLevelId));
    }

    @GetMapping("/organisation-unit-level/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getOrganisationUnitByOrganisationUnitLevelId(@PathVariable Long id, @RequestParam(required = false, defaultValue = "*") String orgUnitName, @PageableDefault(300) Pageable pageable) {
        Page<OrganisationUnit> page = this.organisationUnitService.getOrganisationUnitByOrganisationUnitLevelId(id, orgUnitName, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(this.organisationUnitService.getOrganisationUnitByOrganisationUnitLevelIdPageContent(page), headers, HttpStatus.OK);
    }

    @GetMapping("/organisation-unit-levels/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnit>> getAllOrganisationUnitByOrganisationUnitLevelId(@PathVariable Long id, @RequestParam(required = false, defaultValue = "*") String orgUnitName, @PageableDefault(300) Pageable pageable) {
        Page<OrganisationUnit> page = this.organisationUnitService.getAllOrganisationUnitByOrganisationUnitLevelId(id, orgUnitName, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/hierarchy/{parentOrgUnitId}/{orgUnitLevelId}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnitDTO>> getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId(@PathVariable Long parentOrgUnitId, @PathVariable Long orgUnitLevelId, @PageableDefault(300) Pageable pageable) {
        Page page = this.organisationUnitService.getOrganisationUnitHierarchies(parentOrgUnitId, orgUnitLevelId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(this.organisationUnitService.getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId(page), headers, HttpStatus.OK);
    }

    /*@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(organisationUnitService.delete(id));
    }*/
}
