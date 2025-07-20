package org.nomisng.domain.mapper;

import org.mapstruct.Mapper;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.dto.DomainDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DomainMapper {
    Domain toDomain(DomainDTO domainDTO);

    DomainDTO toDomainDTO(Domain domain);

    List<DomainDTO> toDomainDTOS(List<Domain> domain);

}
