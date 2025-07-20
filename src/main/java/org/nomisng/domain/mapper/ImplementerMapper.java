package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.domain.entity.Implementer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImplementerMapper {
    Implementer toImplementer(ImplementerDTO implementerDTO);

    ImplementerDTO toImplementerDTO(Implementer implementer);

    List<ImplementerDTO> toImplementerDTOS(List<Implementer> implementers);

}
