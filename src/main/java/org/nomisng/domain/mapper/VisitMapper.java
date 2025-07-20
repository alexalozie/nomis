package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.VisitDTO;
import org.nomisng.domain.entity.Visit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VisitMapper {
    Visit toVisit(VisitDTO visitDTO);

    VisitDTO toVisitDTO(Visit visit);

    List<VisitDTO> toVisitDTOS(List<Visit> visit);

}
