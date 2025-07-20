package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.CboProjectLocationDTO;
import org.nomisng.domain.entity.CboProjectLocation;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.service.CboProjectLocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cbo-project-location")
@Slf4j
@RequiredArgsConstructor
public class CboProjectLocationResource {
    private final CboProjectLocationService cboProjectLocationService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnit>> getOrganisationUnitByCboProjectId() {
        return ResponseEntity.ok(cboProjectLocationService.getOrganisationUnitByCboProjectId());
    }

    @GetMapping("/state")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnit>> getState() {
        return ResponseEntity.ok(cboProjectLocationService.getState());
    }

    @GetMapping("/state/{stateId}/lga")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OrganisationUnit>> getLga(@PathVariable Long stateId) {
        return ResponseEntity.ok(cboProjectLocationService.getLga(stateId));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<CboProjectLocation>> update(@Valid @RequestBody List<CboProjectLocationDTO> cboProjectLocationDTOS) {
        return ResponseEntity.ok(cboProjectLocationService.updateCboProjectLocation(cboProjectLocationDTOS));
    }

}
