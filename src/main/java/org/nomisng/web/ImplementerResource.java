package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.service.ImplementerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/implementers")
@Slf4j
@RequiredArgsConstructor
public class ImplementerResource {
    private final ImplementerService implementerService;
    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<ImplementerDTO>> getAllImplementers() {
        return ResponseEntity.ok(implementerService.getAllImplementers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ImplementerDTO> getImplementer(@PathVariable Long id) {
        return ResponseEntity.ok(implementerService.getImplementer(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ImplementerDTO> saveImplementer(@Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.saveImplementer(implementerDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<ImplementerDTO> updateImplementer(@PathVariable Long id, @Valid @RequestBody ImplementerDTO implementerDTO) {
        return ResponseEntity.ok(implementerService.updateImplementer(id, implementerDTO));
    }

}
