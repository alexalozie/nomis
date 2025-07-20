package org.nomisng.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.nomisng.domain.entity.Flag;
import org.nomisng.domain.entity.OrganisationUnit;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class HouseholdDTO {
    private Long id;
    private String uniqueId;
    private String status;
    private Long cboId;
    @NotNull(message = "householdMigrationDTOS is mandatory")
    private List<HouseholdMigrationDTO> householdMigrationDTOS;
    @NotNull(message = "householdMemberDTO is mandatory")
    private HouseholdMemberDTO householdMemberDTO;
    private Object details;
    private Object assessment;
    @NotNull(message = "ward is mandatory")
    private OrganisationUnit ward;
    private Long wardId;
    private Long serialNumber;
    private List<Flag> flags;

}
