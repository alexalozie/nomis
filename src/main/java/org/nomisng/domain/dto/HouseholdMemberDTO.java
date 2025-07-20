package org.nomisng.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomisng.domain.entity.Flag;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class HouseholdMemberDTO {
    private Long id;
    @NotNull(message = "householdId is mandatory")
    private Long householdId;
    @NotNull(message = "householdMemberType is mandatory")
    private Integer householdMemberType;
    private String uniqueId;
    private Object details;
    private int archived;
    private List<Flag> flags;

}
