package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.service.CboProjectService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/cbo-projects")
@Slf4j
@RequiredArgsConstructor
public class CboProjectResource {
    private final CboProjectService cboProjectService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<CboProject>> getAll() {
        return ResponseEntity.ok(cboProjectService.getAll());
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<CboProjectDTO> getCboProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(cboProjectService.getCboProjectById(id));
    }*/
    @GetMapping
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<List<CboProjectDTO>> getCboProjects(@RequestParam(required = false, defaultValue = "0") Long cboId, @RequestParam(required = false, defaultValue = "0") Long donorId, @RequestParam(required = false, defaultValue = "0") Long implementerId, @PageableDefault(30) Pageable pageable) {
        Page<CboProject> page = cboProjectService.getCboProjects(cboId, donorId, implementerId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(cboProjectService.getCboProjectsFromPage(page), headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public ResponseEntity<CboProjectDTO> save(@Valid @RequestBody CboProjectDTO cboProjectDTO) {
        return ResponseEntity.ok(cboProjectService.saveCboProject(cboProjectDTO));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void update(@Valid @RequestBody CboProjectDTO cboProjectDTO, @PathVariable Long id) {
        cboProjectService.updateCboProject(id, cboProjectDTO);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void switchCboProject(@PathVariable Long id) {
        cboProjectService.switchCboProject(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public void delete(@RequestBody CboProjectDTO cboProjectDTO, @PathVariable Long id) {
        cboProjectService.deleteCboProject(id);
    }

}
