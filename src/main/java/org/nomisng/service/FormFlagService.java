package org.nomisng.service;

import org.nomisng.domain.dto.FormFlagDTOS;
import org.nomisng.domain.entity.FormFlag;

import java.util.List;


public interface FormFlagService {
    List<FormFlagDTOS> getAllFlags();

    List<FormFlag> saveFormFlag(FormFlagDTOS formFlagDTOS);

    FormFlag getFormFlagById(Long id);

    FormFlagDTOS getFlagById(Long id);

    FormFlagDTOS updateFormFlag(Long id, FormFlagDTOS formFlagDTOS);

    void deleteFormFlag(Long id);

}
