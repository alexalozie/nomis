package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Donor;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DonorMapper {
    Donor toDonor(DonorDTO donorDTO);

    DonorDTO toDonorDTO(Donor donor);

    List<DonorDTO> toDonorDTOS(List<Donor> donors);

}
