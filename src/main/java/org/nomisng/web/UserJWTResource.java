package org.nomisng.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.JwtResponse;
import org.nomisng.domain.dto.TokenRefreshDTO;
import org.nomisng.domain.entity.RefreshToken;
import org.nomisng.service.RefreshTokenService;
import org.nomisng.web.apierror.RefreshTokenException;
import org.nomisng.web.vm.LoginVM;
import org.nomisng.domain.entity.User;
import org.nomisng.security.jwt.JWTFilter;
import org.nomisng.service.UserJWTService;
import org.nomisng.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class UserJWTResource {
    private final UserJWTService userJWTService;
    private final UserService userService;

    private final RefreshTokenService refreshTokenService;
    private static final String TOKEN_TYPE = "Bearer";

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        String jwt = userJWTService.authorize(loginVM);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        //get user
        User user = userService.getUserWithAuthoritiesByUsername(loginVM.getUsername().trim()).get();
        Long currentCboProjectId = loginVM.getCboProjectId();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        //get all cboProjectIds for the login user
        //        List<Long> cboProjectIds = user.getApplicationUserCboProjects()
        //                .stream()
        //                .map(ApplicationUserCboProject::getCboProjectId)
        //                .collect(Collectors.toList());
        //
        //        //if cboProject does not exist default the cboProject to null
        //        if (!cboProjectIds.contains(currentCboProjectId)) {
        //            currentCboProjectId = null;
        //        }
        //        //set current cbo project
        //        user.setCurrentCboProjectId(currentCboProjectId);
        //        //update user
        //        userService.update(user.getId(), user);
      //return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), TOKEN_TYPE));
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshDTO request) {
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = userJWTService.generateTokenFromUsername(user.getUserName());
                    return ResponseEntity.ok(new JwtResponse(token, requestRefreshToken,TOKEN_TYPE));
                })
                .orElseThrow(() -> new RefreshTokenException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }


    /**
     * Object to return as body in JWT Authentication.
     */
    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}
