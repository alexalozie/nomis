package org.nomisng.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.vm.ManagedUserVM;
import org.nomisng.domain.dto.UserDTO;
import org.nomisng.domain.entity.User;
import org.nomisng.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class AccountResource {
    private final UserService userService;

    @GetMapping("/account")
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\', \'M&E Officer\')")
    public UserDTO getAccount(Principal principal) {
        UserDTO userDTO = userService.getUserWithRoles().map(UserDTO::new).orElseThrow(() -> new EntityNotFoundException(User.class, principal.getName() + "", ""));
        return userDTO;
    }

//<editor-fold defaultstate="collapsed" desc="delombok">
//</editor-fold>
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority(\'System Administrator\',\'Administrator\', \'DEC\')")
    public void registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        //Check Password Length
        userService.save(managedUserVM, managedUserVM.getPassword(), false);
    }

}
