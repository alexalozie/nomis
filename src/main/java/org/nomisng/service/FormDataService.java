package org.nomisng.service;

import org.nomisng.domain.dto.FormDataDTO;

import java.util.List;
import java.util.Optional;

public interface FormDataService {
  FormDataDTO saveFormData(FormDataDTO formDataDTO);
  Optional<FormDataDTO> updateFormData(Long id, FormDataDTO formDataDTO);

  FormDataDTO getFormData(Long id);

  List<FormDataDTO> getAllFormData() ;
  void deleteFormData(Long id);
}
