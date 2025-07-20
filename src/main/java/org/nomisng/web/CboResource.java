package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.CboDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.service.CboService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cbos")
@Slf4j
@RequiredArgsConstructor
public class CboResource {
    private final CboService cboService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<CboDTO>> getAllCbos() {
        return ResponseEntity.ok(cboService.getAllCbos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<CboDTO> getCbo(@PathVariable Long id) {
        return ResponseEntity.ok(cboService.getCbo(id));
    }

    //To check if a cbo exist by code
    @GetMapping("/code/{code}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Boolean> existByCode(@PathVariable String code) {
        return ResponseEntity.ok(cboService.existByCode(code));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Cbo> save(@Valid @RequestBody CboDTO cboDTO) {
        return ResponseEntity.ok(cboService.saveCbo(cboDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Cbo> update(@PathVariable Long id, @Valid @RequestBody CboDTO cboDTO) {
        return ResponseEntity.ok(cboService.updateCbo(id, cboDTO));
    }

}
