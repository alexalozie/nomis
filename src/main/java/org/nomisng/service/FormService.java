package org.nomisng.service;

import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@Transactional
public interface FormService {

    List<FormDTO> getAllForms(Integer formType);

    Form saveForm(FormDTO formDTO);

    FormDTO getForm(Long id);

    FormDTO getFormByFormCode(String formCode);

    Form updateForm(Long id, FormDTO formDTO);

    void deleteForm(Long id);
}
