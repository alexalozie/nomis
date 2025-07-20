package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
import org.nomisng.repository.PermissionRepository;
import org.nomisng.service.FormService;
import org.nomisng.service.UserService;
import org.nomisng.util.AccessRight;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/forms")
@Slf4j
@RequiredArgsConstructor
public class FormResource {
    private final FormService formService;
    private final FormRepository formRepository;
    private final FormMapper formMapper;
    private final AccessRight accessRight;
    private final PermissionRepository permissionRepository;
    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<FormDTO>> getAllForms(@RequestParam(required = false, defaultValue = "3") Integer formType) {
        return ResponseEntity.ok(formService.getAllForms(formType));
    }

    @GetMapping("/formCode")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<FormDTO> getFormByFormCode(@RequestParam String formCode) {
        return ResponseEntity.ok(formService.getFormByFormCode(formCode));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(formService.getForm(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Form> save(@Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.saveForm(formDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<Form> update(@PathVariable Long id, @Valid @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.updateForm(id, formDTO));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@PathVariable Long id) {
        formService.deleteForm(id);
    }

}
