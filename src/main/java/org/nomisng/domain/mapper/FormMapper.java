package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormMapper {
    Form toForm(FormDTO formDTO);

    FormDTO toFormDTO(Form form);

    List<FormDTO> toFormDTOS(List<Form> form);
}
