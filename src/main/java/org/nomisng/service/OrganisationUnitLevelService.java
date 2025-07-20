package org.nomisng.service;

import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.dto.OrganisationUnitLevelDTO;

import java.util.List;


public interface OrganisationUnitLevelService {

    OrganisationUnitLevelDTO saveOrgUnitLevel(OrganisationUnitLevelDTO organisationUnitLevelDTO);

    OrganisationUnitLevelDTO updateOrgUnitLevel(Long id, OrganisationUnitLevelDTO organisationUnitLevelDTO);

    OrganisationUnitLevelDTO getOrganizationUnitLevel(Long id);

    List<OrganisationUnitLevelDTO> getAllOrganizationUnitLevel(Integer status);

    List<OrganisationUnitDTO> getAllOrganisationUnitsByOrganizationUnitLevel(Long id);

    void deleteOrgUnitLevel(Long id);

}
