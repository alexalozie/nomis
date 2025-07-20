package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.service.OvcServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ovc-services")
@Slf4j
@RequiredArgsConstructor
public class OvcServiceResource {
    private final OvcServiceService ovcServiceService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<OvcService> save(@Valid @RequestBody OvcServiceDTO ovcServiceDTO) {
        return ResponseEntity.ok(ovcServiceService.save(ovcServiceDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public OvcService update(@PathVariable Long id, @Valid @RequestBody OvcServiceDTO ovcServiceDTO) {
        return ovcServiceService.update(id, ovcServiceDTO);
    }

    @GetMapping("{id}/domain")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Domain> getDomainByOvcServiceId(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.getDomainByOvcServiceId(id));
    }

    /*@GetMapping("{id}")
    public ResponseEntity<OvcServiceDTO> getOvcServiceByIdOrServiceType(@PathVariable Long id) {
        return ResponseEntity.ok(ovcServiceService.getOvcServiceById(id));
    }*/
    @GetMapping("{serviceType}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<OvcServiceDTO>> getAllOvcServices(@PathVariable(value = "serviceType", required = false) Integer serviceType) {
        if (serviceType != 0) return ResponseEntity.ok(ovcServiceService.getOvcServiceByServiceType(serviceType));
        return ResponseEntity.ok(ovcServiceService.getAllOvcServices());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        ovcServiceService.delete(id);
    }

}
