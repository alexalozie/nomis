package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.ApplicationUserCboProjectDTO;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.nomisng.service.ApplicationUserCboProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/application-user-cbo-project")
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserCboProjectResource {
    private final ApplicationUserCboProjectService applicationUserCboProjectService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\')")
    public ResponseEntity<List<ApplicationUserCboProject>> save(@RequestBody @Valid ApplicationUserCboProjectDTO applicationUserCboProjectDTO) {
        return ResponseEntity.ok(applicationUserCboProjectService.save(applicationUserCboProjectDTO));
    }

}
