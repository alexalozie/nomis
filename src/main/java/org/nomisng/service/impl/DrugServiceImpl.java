package org.nomisng.service.impl;

import org.nomisng.domain.dto.DrugDTO;
import org.nomisng.domain.entity.Drug;
import org.nomisng.domain.mapper.DrugMapper;
import org.nomisng.repository.DrugRepository;
import org.nomisng.service.DrugService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class DrugServiceImpl implements DrugService {
    private final Logger log = LoggerFactory.getLogger(DrugServiceImpl.class);
    private final DrugRepository drugRepository;
    private final DrugMapper drugMapper;

    public DrugServiceImpl(DrugRepository drugRepository, DrugMapper drugMapper) {
        this.drugRepository = drugRepository;
        this.drugMapper = drugMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DrugDTO> findAllDrug(Pageable pageable) {
        return drugRepository.findAll(pageable).map(drugMapper::toDrugDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DrugDTO> findOneDrug(Long id) {
        return drugRepository.findById(id)
                .map(drugMapper::toDrugDTO);

    }

    @Override
    public DrugDTO saveDrug(DrugDTO drugDTO) {
        log.debug("Request to save dRug : {}", drugDTO);
        Drug drug = drugMapper.toDrug(drugDTO);
        drug = drugRepository.save(drug);

        return drugMapper.toDrugDTO(drug);
    }

    @Override
    public void deleteDrug(Long id) {
        drugRepository.deleteById(id);
    }

    @Override
    public Optional<DrugDTO> partialUpdateDrug(DrugDTO drugDTO) {
        log.debug("Request to partially update Associate : {}", drugDTO);

        return drugRepository
                .findById(drugDTO.getId())
                .map(existingDrug -> {
                    drugMapper.partialUpdate(existingDrug, drugDTO);

                    return existingDrug;
                })
                .map(drugRepository::save)
                .map(drugMapper::toDrugDTO);
    }

}
