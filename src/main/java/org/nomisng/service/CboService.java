package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.nomisng.domain.dto.CboDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.mapper.CboMapper;
import org.nomisng.repository.CboRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CboService {
    private final CboRepository cboRepository;
    private final CboMapper cboMapper;

    public List getAllCbos() {
        return cboMapper.toCboDTOS(cboRepository.findAllByArchived(UN_ARCHIVED));
    }

    public Cbo saveCbo(CboDTO cboDTO) {
        cboRepository.findByNameAndArchived(cboDTO.getName(), UN_ARCHIVED).ifPresent(cbo -> {
            throw new RecordExistException(Cbo.class, "Name", "" + cboDTO.getName());
        });
        //Temporary, will be replace with cbo code
        if (cboDTO.getCode() == null) {
            cboDTO.setCode(UUID.randomUUID().toString());
        }
        Cbo cbo = cboMapper.toCbo(cboDTO);
        cbo.setArchived(UN_ARCHIVED);
        return cboRepository.save(cbo);
    }

    public CboDTO getCbo(Long id) {
        Cbo cbo = cboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id + ""));
        return cboMapper.toCboDTO(cbo);
    }

    public boolean existByCode(String code) {
        return cboRepository.existsByCode(code);
    }

    public Cbo updateCbo(Long id, CboDTO cboDTO) {
        Cbo cbo = cboRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id + ""));
        cboDTO.setId(cbo.getId());
        return cboRepository.save(cboMapper.toCbo(cboDTO));
    }

    public void deleteCbo(Long id) {
    }

}
