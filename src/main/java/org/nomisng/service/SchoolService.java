package org.nomisng.service;


import org.nomisng.domain.dto.SchoolDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileInputStream;
import java.util.Optional;

public interface SchoolService {
    Page<SchoolDTO> findAllSchools(Pageable pageable);
    Optional<SchoolDTO> findOneSchool(Long id);
    SchoolDTO saveSchool(SchoolDTO schoolDTO);
    void deleteSchool(Long id);

    Optional<SchoolDTO> partialUpdateSchool(SchoolDTO schoolDTO);

    Boolean checkIfSchoolExists(String name, Long stateId, Long lgaId);
    void importSchoolData(FileInputStream fileInputStream, String fileExtension);

}
