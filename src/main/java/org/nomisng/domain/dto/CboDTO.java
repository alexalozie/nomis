package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CboDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "description is mandatory")
    private String description;
    private String code;
    private int archived;
}
