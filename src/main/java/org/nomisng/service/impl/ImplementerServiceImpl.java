package org.nomisng.service.impl;

import org.nomisng.domain.dto.ImplementerDTO;
import org.nomisng.domain.entity.Implementer;
import org.nomisng.domain.mapper.ImplementerMapper;
import org.nomisng.repository.ImplementerRepository;
import org.nomisng.service.ImplementerService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
public class ImplementerServiceImpl implements ImplementerService {
    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ImplementerServiceImpl.class);
    //</editor-fold>
    private final ImplementerRepository implementerRepository;
    private final ImplementerMapper implementerMapper;

//<editor-fold defaultstate="collapsed" desc="delombok">
//</editor-fold>
    @Override
    public List<ImplementerDTO> getAllImplementers() {
        return implementerMapper.toImplementerDTOS(implementerRepository.findAll());
    }

    @Override
    public ImplementerDTO saveImplementer(ImplementerDTO implementerDTO) {
        implementerRepository.findByNameAndArchived(implementerDTO.getName(), UN_ARCHIVED).ifPresent(implementer -> {
            throw new RecordExistException(Implementer.class, "Name", "" + implementerDTO.getName());
        });
        if (implementerDTO.getCode() == null) {
            implementerDTO.setCode(UUID.randomUUID().toString());
        }
        Implementer implementer = implementerMapper.toImplementer(implementerDTO);
        implementer.setArchived(UN_ARCHIVED);
        implementerRepository.save(implementer);
        return implementerMapper.toImplementerDTO(implementer);
    }

    @Override
    public ImplementerDTO getImplementer(Long id) {
        Implementer implementer = implementerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Implementer.class, "Id", id + ""));
        return implementerMapper.toImplementerDTO(implementer);
    }

    @Override
    public ImplementerDTO updateImplementer(Long id, ImplementerDTO implementerDTO) {
        Implementer implementer = implementerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Implementer.class, "Id", id + ""));
        implementerDTO.setId(implementer.getId());
        implementer = implementerRepository.save(implementerMapper.toImplementer(implementerDTO));
        return implementerMapper.toImplementerDTO(implementer);
    }

    @Override
    public void deleteImplementer(Long id) {
        implementerRepository.deleteById(id);
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public ImplementerServiceImpl(final ImplementerRepository implementerRepository, final ImplementerMapper implementerMapper) {
        this.implementerRepository = implementerRepository;
        this.implementerMapper = implementerMapper;
    }
    //</editor-fold>
}
