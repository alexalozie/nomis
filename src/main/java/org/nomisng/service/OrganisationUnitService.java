package org.nomisng.service;

import org.nomisng.domain.dto.OrganisationUnitDTO;
import org.nomisng.domain.entity.OrganisationUnit;
import org.nomisng.domain.entity.OrganisationUnitHierarchy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface OrganisationUnitService {

    List<OrganisationUnitDTO> saveOrganisationUnit(Long parentOrganisationUnitId, Long organisationUnitLevelId, List<OrganisationUnitDTO> organisationUnitDTOS);

    //TODO: work on this
    OrganisationUnitDTO updateOrganisationUnit(Long id, OrganisationUnitDTO organisationUnitDTO);

    Integer deleteOrganisationUnit(Long id);

    OrganisationUnitDTO getOrganisationUnitById(Long id);

    List<OrganisationUnitDTO> getOrganisationUnitByParentOrganisationUnitId(Long id);
    List<OrganisationUnitDTO> getAllOrganisationUnit();
    List<OrganisationUnitDTO> getOrganisationUnitByParentOrganisationUnitIdAndOrganisationUnitLevelId(Long parentOrgUnitId, Long orgUnitLevelId);

    Page<OrganisationUnit> getOrganisationUnitByOrganisationUnitLevelId(Long organisationUnitLevelId, String orgUnitName, Pageable pageable);

    List<OrganisationUnitDTO> getOrganisationUnitByOrganisationUnitLevelIdPageContent(Page<OrganisationUnit> page);


    Page<OrganisationUnitHierarchy> getOrganisationUnitHierarchies(Long parent_org_unit_id, Long org_unit_level_id, Pageable pageable);

    List<OrganisationUnitDTO> getOrganisationUnitSubsetByParentOrganisationUnitIdAndOrganisationUnitLevelId
            (Page<OrganisationUnitHierarchy> organisationUnitHierarchies);

    Page<OrganisationUnit> getAllOrganisationUnitByOrganisationUnitLevelId(Long organisationUnitLevelId, String orgUnitName, Pageable pageable);


}
