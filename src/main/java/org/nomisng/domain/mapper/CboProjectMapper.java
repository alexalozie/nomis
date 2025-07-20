package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.CboProjectDTO;
import org.nomisng.domain.entity.CboProject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CboProjectMapper {
    CboProject toCboProject(CboProjectDTO cboProjectDTO);

    CboProjectDTO toCboProjectDTO(CboProject cboProject);

    List<CboProjectDTO> toCboProjectDTOS(List<CboProject> cboProjects);
}
