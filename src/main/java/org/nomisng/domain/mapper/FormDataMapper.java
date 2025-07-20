package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FormDataDTO;
import org.nomisng.domain.entity.FormData;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormDataMapper {
    FormData toFormData(FormDataDTO formDataDTO);

    FormDataDTO toFormDataDTO(FormData formData);

    List<FormDataDTO> toFormDataDTOS(List<FormData> formData);

}
