package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.ApplicationCodesetDTO;
import org.nomisng.domain.entity.ApplicationCodeset;
import org.nomisng.domain.mapper.ApplicationCodesetMapper;
import org.nomisng.repository.ApplicationCodesetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ApplicationCodesetService {
    private final ApplicationCodesetRepository applicationCodesetRepository;
    private final ApplicationCodesetMapper applicationCodesetMapper;

    public List<ApplicationCodesetDTO> getAllApplicationCodeset() {
        List<ApplicationCodeset> applicationCodesets = applicationCodesetRepository.findAllByArchivedOrderByIdAsc(UN_ARCHIVED);
        return applicationCodesetMapper.toApplicationCodesetDTOList(applicationCodesets);
    }

    public ApplicationCodeset save(ApplicationCodesetDTO applicationCodesetDTO) {
        Optional<ApplicationCodeset> applicationCodesetOptional = applicationCodesetRepository.findByDisplayAndCodesetGroupAndArchived(applicationCodesetDTO.getDisplay(), applicationCodesetDTO.getCodesetGroup(), UN_ARCHIVED);
        applicationCodesetOptional.ifPresent(applicationCodeset -> {
            throw new RecordExistException(ApplicationCodeset.class, "Display:", applicationCodeset.getDisplay());
        });
        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        applicationCodeset.setArchived(UN_ARCHIVED);
        return applicationCodesetRepository.save(applicationCodeset);
    }

    public List<ApplicationCodesetDTO> getApplicationCodeByCodesetGroup(String codesetGroup) {
        List<ApplicationCodeset> applicationCodesetList = applicationCodesetRepository.findAllByCodesetGroupAndArchivedOrderByIdAsc(codesetGroup, UN_ARCHIVED);
        return applicationCodesetMapper.toApplicationCodesetDTOList(applicationCodesetList);
    }

    public ApplicationCodeset update(Long id, ApplicationCodesetDTO applicationCodesetDTO) {
        applicationCodesetRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(ApplicationCodeset.class, "Display:", id + ""));
        final ApplicationCodeset applicationCodeset = applicationCodesetMapper.toApplicationCodeset(applicationCodesetDTO);
        applicationCodeset.setId(id);
        applicationCodeset.setArchived(UN_ARCHIVED);
        return applicationCodesetRepository.save(applicationCodeset);
    }

    public Integer delete(Long id) {
        ApplicationCodeset applicationCodeset = applicationCodesetRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(ApplicationCodeset.class, "Display:", id + ""));
        applicationCodeset.setArchived(ARCHIVED);
        applicationCodesetRepository.save(applicationCodeset);
        return applicationCodeset.getArchived();
    }

    public Boolean exist(String display, String codesetGroup) {
        return applicationCodesetRepository.existsByDisplayAndCodesetGroup(display, codesetGroup);
    }

}
