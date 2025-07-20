package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.entity.OrganisationUnitLevel;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganisationUnitLevelMapper {
    OrganisationUnitLevelDTO toOrganisationUnitLevelDTO(OrganisationUnitLevel organisationUnitLevel);

    OrganisationUnitLevel toOrganisationUnitLevel(OrganisationUnitLevelDTO organisationUnitLevelDTO);

    List<OrganisationUnitLevelDTO> toOrganisationUnitLevelDTOList(List<OrganisationUnitLevel> organisationUnitLevels);
}
