package org.nomisng.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomisng.domain.entity.FormData;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class EncounterDTO {
    private Long id;
    @NotNull(message = "dateEncounter is mandatory")
    private LocalDateTime dateEncounter;
    @NotBlank(message = "formCode is mandatory")
    private String formCode;
    //@NotNull(message = "householdMemberId is mandatory") not mandatory
    private Long householdMemberId;
    @NotNull(message = "householdId is mandatory")
    private Long householdId;
    private List<FormData> formData;
    private String formName;
    private String firstName;
    private String lastName;
    private String otherNames;

}
