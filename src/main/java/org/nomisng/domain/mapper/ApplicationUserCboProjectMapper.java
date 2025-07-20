package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.ApplicationUserCboProjectDTO;
import org.nomisng.domain.entity.ApplicationUserCboProject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationUserCboProjectMapper {
    ApplicationUserCboProject toApplicationUserCboProject(ApplicationUserCboProjectDTO applicationUserCboProjectDTO);

    ApplicationUserCboProjectDTO toApplicationUserCboProjectDTO(ApplicationUserCboProject applicationUserCboProject);

    List<ApplicationUserCboProjectDTO> toApplicationUserCboProjectDTOS(List<ApplicationUserCboProject> applicationUserCboProjects);
}
