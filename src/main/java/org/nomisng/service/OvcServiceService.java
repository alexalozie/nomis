package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.OvcServiceDTO;
import org.nomisng.domain.entity.Domain;
import org.nomisng.domain.entity.OvcService;
import org.nomisng.domain.mapper.OvcServiceMapper;
import org.nomisng.repository.DomainRepository;
import org.nomisng.repository.OvcServiceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import static org.nomisng.util.Constants.ArchiveStatus.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class OvcServiceService {
    private final OvcServiceRepository ovcServiceRepository;
    private final DomainRepository domainRepository;
    private final OvcServiceMapper ovcServiceMapper;
    private static final Integer BOTH = 3;

    public OvcService save(OvcServiceDTO ovcServiceDTO) {
        Optional<OvcService> optionalOvcService = ovcServiceRepository.findByNameAndServiceTypeAndArchived(ovcServiceDTO.getName(), ovcServiceDTO.getServiceType(), UN_ARCHIVED);
        optionalOvcService.ifPresent(ovcService -> {
            throw new RecordExistException(OvcService.class, "OvcService", ovcServiceDTO.getName() + "");
        });
        final OvcService ovcService = ovcServiceMapper.toOvcService(ovcServiceDTO);
        ovcService.setCode(UUID.randomUUID().toString());
        if (ovcService.getArchived() == null) {
            ovcService.setArchived(UN_ARCHIVED);
        }
        return this.ovcServiceRepository.save(ovcService);
    }

    public List<OvcServiceDTO> getAllOvcServices() {
        List<OvcService> ovcServices =  //setting domain name of an ovcService
        ovcServiceRepository.findAllByArchivedOrderByIdDesc(UN_ARCHIVED).stream().sorted(Comparator.comparing(OvcService::getId).reversed()).map(ovcService -> {
            ovcService.setDomainName(ovcService.getDomainByDomainId().getName());
            return ovcService;
        }).collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }

    /*public List<Form> getFormByOvcServiceId(Long ovcServiceId){
        Optional<OvcService> optionalOvcService = ovcServiceRepository.findById(ovcServiceId);
        if(!optionalOvcService.isPresent() || optionalOvcService.get().getArchived() == ARCHIVED) throw new EntityNotFoundException(OvcService.class, "OvcService Id", ovcServiceId + "");
        List<Form> forms = optionalOvcService.get().getFormByOvcServiceCode().stream()
                .filter(form ->form.getArchived()!= null && form.getArchived()== UN_ARCHIVED)
                .sorted(Comparator.comparing(Form::getId).reversed())
                .collect(Collectors.toList());
        return forms;
    }*/
    public Domain getDomainByOvcServiceId(Long ovcServiceId) {
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(ovcServiceId, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(OvcService.class, "id:", ovcServiceId + ""));
        return ovcService.getDomainByDomainId();
    }

    public void delete(Long id) {
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(OvcService.class, "OvcService Id", id + ""));
        Domain domain = ovcService.getDomainByDomainId();
        if (domain != null) {
            throw new RecordExistException(Domain.class, "Domain" + domain.getName(), "tied to ovcServices");
        }
        ovcService.setArchived(ARCHIVED);
        ovcServiceRepository.save(ovcService);
    }

    public OvcService update(Long id, OvcServiceDTO ovcServiceDTO) {
        ovcServiceRepository.findByIdAndArchived(id, UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(OvcService.class, "OvcService Id", id + ""));
        if (ovcServiceDTO.getArchived() == null) {
            ovcServiceDTO.setArchived(UN_ARCHIVED);
        }
        final OvcService ovcService = ovcServiceMapper.toOvcService(ovcServiceDTO);
        ovcService.setId(id);
        String domainName = domainRepository.findByIdAndArchived(ovcServiceDTO.getDomainId(), UN_ARCHIVED).orElseThrow(() -> new EntityNotFoundException(Domain.class, "Domain Id", ovcServiceDTO.getDomainId() + "")).getName();
        ovcService.setDomainName(domainName);
        return ovcServiceRepository.save(ovcService);
    }

    //TODO: Keep for future usage
    /*public OvcServiceDTO getOvcServiceById(Long id){
        OvcService ovcService = ovcServiceRepository.findByIdAndArchived(id, UN_ARCHIVED)
                .orElseThrow(() -> new EntityNotFoundException(OvcService.class,"id:",id+""));

        return ovcServiceMapper.toOvcServiceDTO(ovcService);
    }*/
    public List<OvcServiceDTO> getOvcServiceByServiceType(Integer serviceType) {
        List<OvcService> ovcServices =  //setting domain name of an ovcService
        ovcServiceRepository.findByArchived(UN_ARCHIVED).stream().filter(ovcService -> (this.filterOvcServiceByServiceType(ovcService, serviceType))).sorted(Comparator.comparing(OvcService::getId).reversed()).map(ovcService -> {
            ovcService.setDomainName(ovcService.getDomainByDomainId().getName());
            return ovcService;
        }).collect(Collectors.toList());
        return ovcServiceMapper.toOvcServiceDTOS(ovcServices);
    }

    private Boolean filterOvcServiceByServiceType(OvcService ovcService, Integer serviceType) {
        Boolean condition = false;
        //not household serviceType ie 4
        if (serviceType < 4) {
            if (ovcService.getServiceType() != null && (ovcService.getServiceType() == serviceType || ovcService.getServiceType() == BOTH)) {
                condition = true;
            } else {
                condition = false;
            }
        } else 
        //is household serviceType 4
        if (ovcService.getServiceType() == serviceType) {
            condition = true;
        }
        return condition;
    }

}
