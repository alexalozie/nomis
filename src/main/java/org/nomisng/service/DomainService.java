package org.nomisng.service;

import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;

import java.util.List;

public interface DomainService {

    public List<DomainDTO> getAllDomains();

    public Domain saveDomain(DomainDTO domainDTO);

    public DomainDTO getDomainById(Long id);

    public DomainDTO getDomainByDomainCode(String domainCode);

    public Domain updateDomain(Long id, DomainDTO domainDTO) ;
    public void deleteDomain(Long id);

    public List<OvcServiceDTO> getOvcServicesByDomainId(Long domainId);

    public List<OvcServiceDTO> getOvcServicesByDomainIdAndServiceType(Long domainId, Integer serviceType);
}
