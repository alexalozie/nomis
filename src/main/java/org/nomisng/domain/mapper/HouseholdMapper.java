package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.dto.HouseholdMemberDTO;
import org.nomisng.domain.dto.HouseholdMigrationDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
import org.nomisng.domain.entity.HouseholdMigration;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdMapper {
    Household toHousehold(HouseholdDTO householdDTO);

    HouseholdDTO toHouseholdDTO(Household household);

    List<HouseholdDTO> toHouseholdDTOS(List<Household> households);

    /*@Mappings({
            @Mapping(source="household.id", target="id")
    })
    HouseholdDTO toHouseholdDTO(Household household, List<HouseholdMember> householdMembers, List<HouseholdAddress> householdAddresses);
*/
    @Mappings({
            @Mapping(source="household.id", target="id")
    })
    HouseholdDTO toHouseholdDTO(Household household, List<HouseholdMemberDTO> householdMemberDTOS, List<HouseholdMigrationDTO> householdMigrationDTOS);


    @Mappings({
            @Mapping(source="householdDTO.id", target="id")
    })
    Household toHousehold(HouseholdDTO householdDTO, List<HouseholdMemberDTO> householdMemberDTOS, HouseholdMigrationDTO householdMigrationDTO);

    @Mappings({
            @Mapping(source="householdDTO.id", target="id")
    })
    Household toHousehold(HouseholdDTO householdDTO, List<HouseholdMember> householdMembers, List<HouseholdMigration> householdMigrations);

}
