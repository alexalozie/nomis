package org.nomisng.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Cbo;
import org.nomisng.domain.entity.Donor;
import org.nomisng.domain.mapper.DonorMapper;
import org.nomisng.repository.DonorRepository;
import org.nomisng.service.DonorService;
import org.nomisng.web.apierror.EntityNotFoundException;
import org.nomisng.web.apierror.RecordExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.nomisng.util.Constants.ArchiveStatus.ARCHIVED;
import static org.nomisng.util.Constants.ArchiveStatus.UN_ARCHIVED;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DonorServiceImpl implements DonorService {
    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DonorDTO> getAllDonors() {
        return donorMapper.toDonorDTOS(donorRepository.findAllByArchivedOrderByIdDesc(UN_ARCHIVED));
    }

    @Override
    public Donor saveDonor(DonorDTO donorDTO) {
        donorRepository.findByNameAndArchived(donorDTO.getName(), UN_ARCHIVED).ifPresent(donor -> {
            throw new RecordExistException(Donor.class, "Name", "" + donorDTO.getName());
        });
        //Temporary, will be replace with donor code
        if (StringUtils.isBlank(donorDTO.getCode())) {
            donorDTO.setCode(UUID.randomUUID().toString());
        }
        Donor donor = donorMapper.toDonor(donorDTO);
        donor.setArchived(UN_ARCHIVED);
        return donorRepository.save(donor);
    }

    @Override
    @Transactional(readOnly = true)
    public DonorDTO getDonor(Long id) {
        Donor donor = donorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Donor.class, "Id", id + ""));
        return donorMapper.toDonorDTO(donor);
    }

    @Override
    public Donor updateDonor(Long id, DonorDTO donorDTO) {
        Donor donor = donorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Cbo.class, "Id", id + ""));
        donorDTO.setId(donor.getId());
        return donorRepository.save(donorMapper.toDonor(donorDTO));
    }

    @Override
    public void deleteDonor(Long id) {
        Optional<Donor> donor = donorRepository.findById(id);
        if (donor.isPresent()) {
            Donor updateDonor = donor.get();
            updateDonor.setArchived(ARCHIVED);
            donorRepository.save(updateDonor);
        }
    }

}
