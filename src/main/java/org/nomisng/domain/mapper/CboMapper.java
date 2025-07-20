package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.CboDTO;
import org.nomisng.domain.entity.Cbo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CboMapper {
    Cbo toCbo(CboDTO cboDTO);

    CboDTO toCboDTO(Cbo cbo);

    List<CboDTO> toCboDTOS(List<Cbo> cbos);

}
