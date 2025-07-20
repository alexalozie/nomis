package org.nomisng.domain.mapper;



import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FormFlagDTO;
import org.nomisng.domain.dto.FormFlagDTOS;
import org.nomisng.domain.entity.FormFlag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FormFlagMapper {

    FormFlag toFormFlag(FormFlagDTO formFlagDTO);

    FormFlagDTO toFormFlagDTO(FormFlag formFlag);

    List<FormFlagDTO> toFormFlagDTOs(List<FormFlag> formFlags);

    FormFlag toFormFlagDTOS(FormFlagDTOS formFlagDTOS);

}
