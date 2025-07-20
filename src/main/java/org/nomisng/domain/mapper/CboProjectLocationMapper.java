package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.CboProjectLocationDTO;
import org.nomisng.domain.entity.CboProjectLocation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CboProjectLocationMapper {
    CboProjectLocation toCboProjectLocation(CboProjectLocationDTO cboProjectLocationDTO);

    CboProjectLocationDTO toCboProjectLocationDTO(CboProjectLocation cboProjectLocation);

    List<CboProjectLocationDTO> toCboProjectLocationDTOS(List<CboProjectLocation> cboProjectLocations);

    List<CboProjectLocation> toCboProjectLocation(List<CboProjectLocationDTO> cboProjectLocationDTOS);

}
