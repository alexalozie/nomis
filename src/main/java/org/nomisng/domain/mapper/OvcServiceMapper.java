package org.nomisng.domain.mapper;


import org.mapstruct.Mapper;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.OvcService;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OvcServiceMapper {
    OvcServiceDTO toOvcServiceDTO(OvcService ovcService);
    OvcService toOvcService(OvcServiceDTO ovcServiceDTO);

    List<OvcServiceDTO> toOvcServiceDTOS(List<OvcService> ovcServices);
}
