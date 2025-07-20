package org.nomisng.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class TokenRefreshDTO {
    @NotBlank
    private String refreshToken;

}
