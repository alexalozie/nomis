package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.EncounterDTO;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.Encounter;
import org.nomisng.service.EncounterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/encounters")
@Slf4j
@RequiredArgsConstructor
public class EncounterResource {
    private final EncounterService encounterService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<EncounterDTO>> getAllEncounters() {
        return ResponseEntity.ok(encounterService.getAllEncounters());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<EncounterDTO> getEncounterById(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getEncounterById(id));
    }

    @GetMapping("{id}/FormData")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<FormDataDTO>> getFormDataByEncounterId(@PathVariable Long id) {
        return ResponseEntity.ok(encounterService.getFormDataByEncounterId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Encounter> save(@Valid @RequestBody EncounterDTO encounterDTO) {
        return ResponseEntity.ok(encounterService.save(encounterDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Encounter> update(@PathVariable Long id, @Valid @RequestBody EncounterDTO encounterDTO) {
        return ResponseEntity.ok(encounterService.update(id, encounterDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        encounterService.delete(id);
    }

}
