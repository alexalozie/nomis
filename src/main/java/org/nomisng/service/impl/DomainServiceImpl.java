package org.nomisng.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.service.DomainService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.DomainDTO;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.domain.mapper.DomainMapper;
import org.nomisng.domain.mapper.OvcServiceMapper;
import org.nomisng.repository.DomainRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService {
    private final DomainRepository domainRepository;
    private final DomainMapper domainMapper;
    private final OvcServiceMapper ovcServiceMapper;
    private static final Integer BOTH = 3;

    @Override
    @Transactional(readOnly = true)
    public List<DomainDTO> getAllDomains() {
        return domainRepository.findAllByArchivedOrderByNameAsc(UN_ARCHIVED);
    }

    @Override
    public Domain saveDomain(DomainDTO domainDTO) {
        Optional<Domain> domainOptional = domainRepository.findByNameAndArchived(domainDTO.getName(), UN_ARCHIVED);
        domainOptional.ifPresent(domain -> {
            throw new RecordExistException(Domain.class, "Name", domainDTO.getName());
        });
        domainDTO.setCode(UUID.randomUUID().toString());
        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setArchived(UN_ARCHIVED);
        return domainRepository.save(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainDTO getDomainById(Long id) {
        Domain domain = domainRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", id + ""));
        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public DomainDTO getDomainByDomainCode(String domainCode) {
        Domain domain = domainRepository.findByCodeAndArchived(domainCode, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Domain Code", domainCode));
        DomainDTO domainDTO = domainMapper.toDomainDTO(domain);
        return domainDTO;
    }

    @Override
    public Domain updateDomain(Long id, DomainDTO domainDTO) {
        domainRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", id + ""));
        Domain domain = domainMapper.toDomain(domainDTO);
        domain.setId(id);
        return domainRepository.save(domain);
    }

    @Override
    public void deleteDomain(Long id) {
        Domain domain = domainRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", id + ""));
        List<OvcService> ovcServices = domain.getServicesById();
        if (!ovcServices.isEmpty()) {
            throw new RecordExistException(OvcService.class, "ovcServices", "tied to domain");
        }
        domain.setArchived(ARCHIVED);
        domainRepository.save(domain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OvcServiceDTO> getOvcServicesByDomainId(Long domainId) {
        Domain domain = domainRepository.findByIdAndArchived(domainId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", domainId + ""));
        List<OvcService> ovcServices =  //setting domain name of an ovcService
        domain.getServicesById().stream().filter(ovcService -> ovcService.getArchived() != null && ovcService.getArchived() == UN_ARCHIVED).sorted(Comparator.comparing(OvcService::getId).reversed()).map(ovcService -> {
            ovcService.setDomainName(domain.getName());
            return ovcService;
        }).collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }
    @Override
    @Transactional(readOnly = true)
    public List<OvcServiceDTO> getOvcServicesByDomainIdAndServiceType(Long domainId, Integer serviceType) {
        Domain domain = domainRepository.findByIdAndArchived(domainId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Id", domainId + ""));
        List<OvcService> ovcServices =  //setting domain name of an ovcService
        domain.getServicesById().stream().filter(ovcService -> ovcService.getArchived() != null && ovcService.getArchived() == UN_ARCHIVED && (ovcService.getServiceType() != null && ovcService.getServiceType() == serviceType || ovcService.getServiceType() == BOTH)).sorted(Comparator.comparing(OvcService::getId).reversed()).map(ovcService -> {
            ovcService.setDomainName(domain.getName());
            return ovcService;
        }).collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }

}
