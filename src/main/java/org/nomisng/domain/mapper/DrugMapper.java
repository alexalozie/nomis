package org.nomisng.domain.mapper;

import org.mapstruct.*;
import org.nomisng.domain.dto.DrugDTO;
import org.nomisng.domain.entity.Drug;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DrugMapper {
    Drug toDrug(DrugDTO drugDTO);

    DrugDTO toDrugDTO(Drug drug);

    List<DrugDTO> toDrugDTOList(List<Drug> drugs);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Drug entity, DrugDTO dto);

}
