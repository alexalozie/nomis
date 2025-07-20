package org.nomisng.domain.mapper;


import org.mapstruct.Mapper;
import org.nomisng.domain.dto.FlagDTO;
import org.nomisng.domain.entity.Flag;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlagMapper {

    Flag toFlag(FlagDTO flagDTO);

    FlagDTO toFlagDTO(Flag flag);

    List<FlagDTO> toFlagDTOList(List<Flag> flags);
}
