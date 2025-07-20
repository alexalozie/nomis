package org.nomisng.service;

import org.nomisng.domain.dto.ImplementerDTO;
import java.util.List;


public interface ImplementerService {
    List getAllImplementers();
    ImplementerDTO saveImplementer(ImplementerDTO implementerDTO);

    ImplementerDTO getImplementer(Long id);


    ImplementerDTO updateImplementer(Long id, ImplementerDTO implementerDTO);

    void deleteImplementer(Long id);
}
