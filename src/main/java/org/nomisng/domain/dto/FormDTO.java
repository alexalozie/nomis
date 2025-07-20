package org.nomisng.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FormDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    private String code;
    private Object resourceObject;
    private String resourcePath;
    private String supportServices;
    @NotBlank(message = "version is mandatory")
    private String version;
    private int archived;
    //1 - caregiver, 2 - ovc, 3 both
    @NotNull(message = "formType is mandatory")
    private Integer formType;

}
