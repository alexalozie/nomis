package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.HouseholdMigrationDTO;
import org.nomisng.domain.entity.HouseholdMigration;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HouseholdMigrationMapper {
    HouseholdMigration toHouseholdContact(HouseholdMigrationDTO householdMigrationDTO);

    List<HouseholdMigration> toHouseholdMigration(List<HouseholdMigrationDTO> householdMigrationDTOS);

    HouseholdMigrationDTO toHouseholdMigrationDTO(HouseholdMigration householdMigration);

    List<HouseholdMigrationDTO> toHouseholdMigrationDTOS(List<HouseholdMigration> householdMigrations);
}
