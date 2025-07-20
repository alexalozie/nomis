package org.nomisng.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.service.FormService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.nomisng.domain.entity.Permission;
import org.nomisng.domain.mapper.FormMapper;
import org.nomisng.repository.FormRepository;
import org.nomisng.repository.PermissionRepository;
import org.nomisng.util.AccessRight;
import org.nomisng.util.Constants;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FormServiceImpl implements FormService {
    private final FormRepository formRepository;
    private final FormMapper formMapper;
    private final AccessRight accessRight;
    private final PermissionRepository permissionRepository;
    private static final String READ = "Read";
    private static final String WRITE = "Write";
    private static final String DELETE = "Delete";
    private static final String UNDERSCORE = "_";
    private static final int FORM_TYPE_CAREGIVER = 1;
    private static final int FORM_TYPE_VC = 2;
    private static final int FORM_TYPE_BOTH = 3;

    public List<FormDTO> getAllForms(Integer formType) {
        List<Form> forms;
        List<Integer> type = new ArrayList<>();
        type.add(formType);
        switch (formType) {
            case FORM_TYPE_CAREGIVER:
            case FORM_TYPE_VC:
                type.add(FORM_TYPE_BOTH);
                forms = formRepository.findAllByArchivedAndFormTypeOrderByIdAsc(UN_ARCHIVED, type);
                break;
            default:
                type.add(FORM_TYPE_CAREGIVER);
                type.add(FORM_TYPE_VC);
                type.add(FORM_TYPE_BOTH);
                forms = formRepository.findAllByArchivedAndFormTypeOrderByIdAsc(UN_ARCHIVED, type);
                break;
        }
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        return this.getForms(forms, permissions);
    }

    public Form saveForm(FormDTO formDTO) {
        if (formDTO.getCode() == null || formDTO.getCode().isEmpty()) {
            formDTO.setCode(UUID.randomUUID().toString());
        }
        Optional<Form> formOptional = formRepository.findByNameAndArchived(formDTO.getName(), Constants.ArchiveStatus.UN_ARCHIVED);
        formOptional.ifPresent(form -> {
            throw new RecordExistException(Form.class, "Name", form.getName());
        });
        Form form = formMapper.toForm(formDTO);
        form.setArchived(UN_ARCHIVED);
        String read = UNDERSCORE + READ;
        String write = UNDERSCORE + WRITE;
        String delete = UNDERSCORE + DELETE;
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission(formDTO.getCode() + read, formDTO.getName() + " Read", UN_ARCHIVED));
        permissions.add(new Permission(formDTO.getCode() + write, formDTO.getName() + " Write", UN_ARCHIVED));
        permissions.add(new Permission(formDTO.getCode() + delete, formDTO.getName() + " Delete", UN_ARCHIVED));
        permissionRepository.saveAll(permissions);
        form.setArchived(UN_ARCHIVED);
        return formRepository.save(form);
    }

    public FormDTO getForm(Long id) {
        Form form = this.formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id + ""));
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        accessRight.grantAccess(form.getCode(), org.nomisng.service.FormService.class, permissions);
        return formMapper.toFormDTO(form);
    }

    public FormDTO getFormByFormCode(String formCode) {
        Set<String> permissions = accessRight.getAllPermissionForCurrentUser();
        accessRight.grantAccess(formCode, org.nomisng.service.FormService.class, permissions);
        Form form = this.formRepository.findByCodeAndArchived(formCode, Constants.ArchiveStatus.UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Form.class, "formCode", formCode + ""));
        return formMapper.toFormDTO(form);
    }

    public Form updateForm(Long id, FormDTO formDTO) {
        Form form = null;
        try {
            formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id + ""));
            formDTO.setId(id);
            if (StringUtils.isBlank(formDTO.getCode())) {
                throw new EntityNotFoundException(Form.class, "Code", formDTO.getCode() + "");
            }
            form = formRepository.save(formMapper.toForm(formDTO));
        } catch (DataAccessException exception) {
            exception.printStackTrace();
        }
        return form;
    }

    public void deleteForm(Long id) {
        Form form = formRepository.findByIdAndArchived(id, Constants.ArchiveStatus.UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Form.class, "Id", id + ""));
        form.setArchived(Constants.ArchiveStatus.UN_ARCHIVED);
        formRepository.save(form);
    }

    private List<FormDTO> getForms(List<Form> forms, Set<String> permissions) {
        List<FormDTO> formList = new ArrayList<>();
        forms.forEach(form -> {
            if (!accessRight.grantAccessForm(form.getCode(), permissions)) {
                return;
            }
            final FormDTO formDTO = formMapper.toFormDTO(form);
            formList.add(formDTO);
        });
        return formList;
    }

}
