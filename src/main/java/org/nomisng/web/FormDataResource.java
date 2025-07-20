package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.service.FormDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/form-data")
@RequiredArgsConstructor
@Slf4j
public class FormDataResource {
    private final FormDataService formDataService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<FormDataDTO>> getAllFormData() {
        return ResponseEntity.ok(formDataService.getAllFormData());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<FormDataDTO> getFormData(@PathVariable Long id) {
        return ResponseEntity.ok(formDataService.getFormData(id));
    }

    @PostMapping
    public ResponseEntity<FormDataDTO> save(@RequestBody FormDataDTO formDataDTO) {
        return ResponseEntity.ok(formDataService.saveFormData(formDataDTO));

    }
    @PutMapping("{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Optional<FormDataDTO>> updateFormData(@PathVariable Long id, @Valid @RequestBody FormDataDTO formDataDTO) {
        return ResponseEntity.ok(formDataService.updateFormData(id, formDataDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        formDataService.deleteFormData(id);
    }


}
