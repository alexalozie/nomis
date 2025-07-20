package org.nomisng.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.service.FlagService;
import org.nomisng.service.FormFlagService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.FormFlagDTOS;
import org.nomisng.domain.entity.Flag;
import org.nomisng.domain.entity.FormFlag;
import org.nomisng.domain.mapper.FlagMapper;
import org.nomisng.domain.mapper.FormFlagMapper;
import org.nomisng.repository.FlagRepository;
import org.nomisng.repository.FormFlagRepository;
import org.nomisng.repository.MemberFlagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FormFlagServiceImpl implements FormFlagService {
    private static final int UN_ARCHIVED = 0;
    private final FormFlagRepository formFlagRepository;
    private final FormFlagMapper formFlagMapper;
    private final FlagService flagService;
    private final FlagMapper flagMapper;
    private final FlagRepository flagRepository;
    private final MemberFlagRepository memberFlagRepository;

    public List<FormFlagDTOS> getAllFlags() {
        List<FormFlagDTOS> formFlagDTOSList = new ArrayList<>();
        flagRepository.findAllByArchived(UN_ARCHIVED).forEach(flag -> {
            FormFlagDTOS formFlagDTOS = new FormFlagDTOS();
            formFlagDTOS.setFlag(flag);
            formFlagDTOS.setFormFlagDTOS(formFlagMapper.toFormFlagDTOs(flag.getFormsByIdFlag()));
            formFlagDTOSList.add(formFlagDTOS);
        });
        return formFlagDTOSList;
    }

    public List<FormFlag> saveFormFlag(FormFlagDTOS formFlagDTOS) {
        List<FormFlag> formFlags = new ArrayList<>();
        Flag flag = new Flag();
        if (formFlagDTOS.getFlag() != null) {
            flag = flagService.saveFlag(flagMapper.toFlagDTO(formFlagDTOS.getFlag()));
        }
        final Flag finalFlag = flag;
        formFlagDTOS.getFormFlagDTOS().forEach(formFlagDTO -> {
            Optional<FormFlag> optionalFormFlag = formFlagRepository.findByFormCodeAndFlagIdAndStatusAndArchived(formFlagDTO.getFormCode(), finalFlag.getId(), formFlagDTO.getStatus(), UN_ARCHIVED);
            if (optionalFormFlag.isPresent()) throw new RecordExistException(FormFlag.class, "Form and Flag", "");
            formFlagDTO.setFlagId(finalFlag.getId());
            formFlags.add(formFlagMapper.toFormFlag(formFlagDTO));
        });
        if (!formFlags.isEmpty()) {
            return formFlagRepository.saveAll(formFlags);
        }
        return formFlags;
    }

    public FormFlag getFormFlagById(Long id) {
        return formFlagRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Flag.class, "Id", id + ""));
    }

    public FormFlagDTOS getFlagById(Long id) {
        Flag flag = flagRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Flag.class, "Id", id + ""));
        FormFlagDTOS formFlagDTOS = new FormFlagDTOS();
        formFlagDTOS.setFlag(flag);
        formFlagDTOS.setFormFlagDTOS(formFlagMapper.toFormFlagDTOs(flag.getFormsByIdFlag()));
        return formFlagDTOS;
    }

    public FormFlagDTOS updateFormFlag(Long id, FormFlagDTOS formFlagDTOS) {
        List<FormFlag> formFlags = new ArrayList<>();
        Flag flag = new Flag();
        final Flag finalFlag = new Flag();
        if (formFlagDTOS.getFlag() != null) {
            if (formFlagDTOS.getFlag().getContinuous() == null) formFlagDTOS.getFlag().setContinuous(false);
            flag = flagService.updateFlag(id, flagMapper.toFlagDTO(formFlagDTOS.getFlag()));
        }
        finalFlag.setId(flag.getId());
        formFlagDTOS.getFormFlagDTOS().forEach(formFlagDTO -> {
            Optional<FormFlag> optionalFormFlag = formFlagRepository.findByFlagIdAndFormCodeAndArchived(id, formFlagDTO.getFormCode(), UN_ARCHIVED);
            optionalFormFlag.ifPresent(formFlag -> formFlagDTO.setId(formFlag.getId()));
            formFlagDTO.setFlagId(finalFlag.getId());
            formFlags.add(formFlagMapper.toFormFlag(formFlagDTO));
        });
        if (!formFlags.isEmpty()) {
            formFlagDTOS.setFormFlagDTOS(formFlagMapper.toFormFlagDTOs(formFlagRepository.saveAll(formFlags)));
        }
        formFlagDTOS.setFlag(flag);
        return formFlagDTOS;
    }

    public void deleteFormFlag(Long id) {
        Flag flag = flagRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Flag.class, "Id", id + ""));
        memberFlagRepository.deleteByFlagId(flag.getId());
        formFlagRepository.deleteByFlagId(flag.getId());
        flagRepository.delete(flag);
    }

}
