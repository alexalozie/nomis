package org.nomisng.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApplicationCodesetDTO {
    private Long id;
    @NotBlank(message = "codesetGroup is mandatory")
    private String codesetGroup;
    @NotBlank(message = "language is mandatory")
    private String language;
    @NotBlank(message = "display is mandatory")
    private String display;
    @NotBlank(message = "version is mandatory")
    private String version;
    private String code;

}
