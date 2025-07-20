package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FormFlagDTO {
    private Long id;
    private Integer status;
    @NotNull(message = "formCode cannot be null")
    private String formCode;
    private Long flagId;
}
