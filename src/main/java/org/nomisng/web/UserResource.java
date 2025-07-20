package org.nomisng.web;

import com.github.dockerjava.api.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.nomisng.web.vm.ManagedUserVM;
import org.nomisng.domain.dto.UserDTO;
import org.nomisng.domain.entity.CboProject;
import org.nomisng.domain.entity.Role;
import org.nomisng.repository.UserRepository;
import org.nomisng.service.UserService;
import org.nomisng.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserResource {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\')")
    public UserDTO getAccount(Principal principal) {
        return userService.getAccount(principal.getName());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody ManagedUserVM managedUserVM) {
        //Check Password Length
        userService.save(managedUserVM, managedUserVM.getPassword(), false);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        final Page<UserDTO> page = userService.getAllManagedUsers(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/{roleName}/roles-name")
    public ResponseEntity<List<UserDTO>> getUserByRoleName(@PathVariable String roleName) {
        return ResponseEntity.ok(userService.findUsersByRoleName(roleName));
    }

    @GetMapping("/{id}/cbo-projects")
    public ResponseEntity<List<CboProject>> getCboProjectByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getCboProjectByUserId(id));
    }

    @GetMapping("/{id}/roles")
    public ResponseEntity<List<Role>> getRolesByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getRolesByUserId(id));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<Set<Role>> updateRoles(@PathVariable Long id, @Valid @RequestBody List<Role> roles) throws Exception {
        return ResponseEntity.ok(userService.updateRoles(id, roles));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
        userRepository.deleteById(id);
    }

    @GetMapping("/cbo-project/{cboProjectId}")
    public ResponseEntity<List<UserDTO>> getUsersByCboProjectId(@PathVariable Long cboProjectId) {
        if (cboProjectId == null) {
            throw new BadRequestException("Invalid cbo project id");
        }
        return ResponseEntity.ok(userService.getUserByCboProjectId(cboProjectId));
    }

    @GetMapping("/current-cbo-project")
    public ResponseEntity<List<UserDTO>> getUsersByCurrentUserCboProject() {
        return ResponseEntity.ok(userService.getUsersByCurrentUserCboProject());
    }

}
