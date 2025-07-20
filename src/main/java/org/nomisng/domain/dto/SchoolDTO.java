package org.nomisng.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SchoolDTO {
    private Long id;
    @NotNull(message = "School state is required")
    private Long stateId;
    @NotNull(message = "School Lga is required")
    private Long lgaId;
    @NotNull(message = "School name is required")
    private String name;

    private String type;
    private String address;
    private Integer archived = 0;

}
