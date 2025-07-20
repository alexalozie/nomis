package org.nomisng.domain.mapper;


import org.mapstruct.Mapper;
import org.nomisng.domain.dto.ApplicationCodesetDTO;
import org.nomisng.domain.entity.ApplicationCodeset;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApplicationCodesetMapper {

    ApplicationCodeset toApplicationCodeset(ApplicationCodesetDTO applicationCodesetDTO);

    ApplicationCodesetDTO toApplicationCodesetDTO(ApplicationCodeset applicationCodeset);

    List<ApplicationCodesetDTO> toApplicationCodesetDTOList(List<ApplicationCodeset> applicationCodesets);
}
