package org.nomisng.service;

import org.nomisng.domain.dto.DonorDTO;
import org.nomisng.domain.entity.Donor;

import java.util.List;


public interface DonorService {
    public List<DonorDTO> getAllDonors() ;
    public Donor saveDonor(DonorDTO donorDTO);
    public DonorDTO getDonor(Long id);
    public Donor updateDonor(Long id, DonorDTO donorDTO) ;
    public void deleteDonor(Long id) ;

}
