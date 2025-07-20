package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.service.DomainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/domains")
@Slf4j
@RequiredArgsConstructor
public class DomainResource {
    private final DomainService domainService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<DomainDTO>> getAllDomains() {
        return ResponseEntity.ok(domainService.getAllDomains());
    }

    @GetMapping("/domainCode")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<DomainDTO> getDomainByDomainCode(@RequestParam String domainCode) {
        return ResponseEntity.ok(domainService.getDomainByDomainCode(domainCode));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<DomainDTO> getDomainById(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.getDomainById(id));
    }

    @GetMapping("{id}/ovcServices")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OvcServiceDTO>> getOvcServicesByDomainId(@PathVariable Long id) {
        return ResponseEntity.ok(domainService.getOvcServicesByDomainId(id));
    }

    @GetMapping("{id}/ovcServices/{serviceType}")
    public ResponseEntity<List<OvcServiceDTO>> getOvcServicesByDomainIdAndServiceType(@PathVariable Long id, @PathVariable Integer serviceType) {
        return ResponseEntity.ok(domainService.getOvcServicesByDomainIdAndServiceType(id, serviceType));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Domain> save(@Valid @RequestBody DomainDTO domainDTO) {
        return ResponseEntity.ok(domainService.saveDomain(domainDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Domain> update(@PathVariable Long id, @Valid @RequestBody DomainDTO domainDTO) {
        return ResponseEntity.ok(domainService.updateDomain(id, domainDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        domainService.deleteDomain(id);
    }

}
