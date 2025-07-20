package org.nomisng.service.impl;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.FormData;
import org.nomisng.domain.mapper.FormDataMapper;
import org.nomisng.repository.FormDataRepository;
import org.nomisng.service.FormDataService;
import org.nomisng.service.vm.DataHelper;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
public class FormDataServiceImpl implements FormDataService {
    private final FormDataRepository formDataRepository;
    private final FormDataMapper formDataMapper;
    private final DataHelper dataHelper;

    @Override
    public FormDataDTO saveFormData(FormDataDTO formDataDTO) {
        FormData formData = formDataMapper.toFormData(formDataDTO);
        formData = formDataRepository.save(formData);
        return formDataMapper.toFormDataDTO(formData);
    }

    public Optional<FormDataDTO> updateFormData(Long id, FormDataDTO formDataDTO) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        formDataRepository.findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id + ""));
        FormData formData = formDataMapper.toFormData(formDataDTO);
        formData.setId(id);
        formData.setArchived(UN_ARCHIVED);
        formData.setCboProjectId(cboProjectId);

        return formDataRepository
                .findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED)
                .map(existingFormData -> {
                    formDataMapper.toFormData(formDataDTO);
                    formData.setId(id);
                    formData.setArchived(UN_ARCHIVED);
                    formData.setCboProjectId(cboProjectId);

                    return existingFormData;
                })
                .map(formDataRepository::save)
                .map(formDataMapper::toFormDataDTO);
    }

    public FormDataDTO getFormData(Long id) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();

         FormData formData = formDataRepository
                 .findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED)
                 .orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id + ""));

         return formDataMapper.toFormDataDTO(formData);
    }

    public List<FormDataDTO> getAllFormData() {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        List<FormData> formDataList = formDataRepository
                .findAllByArchivedAndCboProjectIdOrderByIdDesc(UN_ARCHIVED, cboProjectId);

        return formDataMapper.toFormDataDTOS(formDataList);
    }

    public void deleteFormData(Long id) {
        Long cboProjectId = dataHelper.getCurrentCboProjectId();
        FormData formData = formDataRepository.findByIdAndCboProjectIdAndArchived(id, cboProjectId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(FormData.class, "Id", id + ""));
        formData.setArchived(ARCHIVED);
        formDataRepository.save(formData);
    }

}
