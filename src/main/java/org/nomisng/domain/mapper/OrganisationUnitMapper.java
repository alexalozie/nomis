package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.entity.OrganisationUnit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganisationUnitMapper {
    OrganisationUnit toOrganisationUnit(OrganisationUnitDTO organisationUnitDTO);

    OrganisationUnitDTO toOrganisationUnitDTO(OrganisationUnit organisationUnit);

    List<OrganisationUnitDTO> toOrganisationUnitDTOList(List<OrganisationUnit> organisationUnits);

    List<OrganisationUnit> toOrganisationUnitList(List<OrganisationUnitDTO> organisationUnitDTOS);

}
