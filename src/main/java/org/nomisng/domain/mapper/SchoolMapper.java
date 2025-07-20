package org.nomisng.domain.mapper;

import org.mapstruct.*;
import org.nomisng.domain.dto.SchoolDTO;
import org.nomisng.domain.entity.School;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    School toSchool(SchoolDTO schoolDTO);

    SchoolDTO toSchoolDTO(School school);

    List<SchoolDTO> toSchoolDTOList(List<School> schools);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget School entity, SchoolDTO dto);

}
