package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.domain.dto.ApplicationUserCboProjectDTO;
import org.nomisng.repository.*;
import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ApplicationUserCboProjectService {
    private final ApplicationUserCboProjectRepository applicationUserCboProjectRepository;

    //assign a cboProject to a user
    public List<ApplicationUserCboProject> save(ApplicationUserCboProjectDTO applicationUserCboProjectDTO) {
        List<ApplicationUserCboProject> applicationUserCboProjects = applicationUserCboProjectRepository.findAllByApplicationUserIdAndArchivedOrderByIdAsc(applicationUserCboProjectDTO.getApplicationUserId(), UN_ARCHIVED);
        if (!applicationUserCboProjects.isEmpty()) {
            applicationUserCboProjectRepository.deleteAll(applicationUserCboProjects);
            applicationUserCboProjects.clear();
        }
        applicationUserCboProjectDTO.getCboProjectIds().forEach(cboProjectId -> {
            final ApplicationUserCboProject applicationUserCboProject = new ApplicationUserCboProject();
            applicationUserCboProject.setApplicationUserId(applicationUserCboProjectDTO.getApplicationUserId());
            applicationUserCboProject.setCboProjectId(cboProjectId);
            applicationUserCboProject.setArchived(UN_ARCHIVED);
            applicationUserCboProjects.add(applicationUserCboProject);
        });
        return applicationUserCboProjectRepository.saveAll(applicationUserCboProjects);
    }

}
