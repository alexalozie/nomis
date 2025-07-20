package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.domain.dto.VisitDTO;
import org.nomisng.domain.entity.Visit;
import org.nomisng.domain.mapper.VisitMapper;
import org.nomisng.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public List getAllVisits() {
        return visitMapper.toVisitDTOS(visitRepository.findAll());
    }

    public Visit save(VisitDTO visitDTO) {
        return visitRepository.save(visitMapper.toVisit(visitDTO));
    }

    public VisitDTO getVisitById(Long id) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Visit.class, "Id", id + ""));
        return visitMapper.toVisitDTO(visit);
    }

    public Visit update(Long id, VisitDTO visitDTO) {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Visit.class, "Id", id + ""));
        visitDTO.setId(visit.getId());
        return visitRepository.save(visitMapper.toVisit(visitDTO));
    }

    public void delete(Long id) {
    }

}
