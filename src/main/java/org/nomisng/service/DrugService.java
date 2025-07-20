package org.nomisng.service;


import org.nomisng.domain.dto.DrugDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface DrugService {
    /**
     * Save a drug.
     *
     * @param drugDTO the entity to save.
     * @return the persisted entity.
     */
    DrugDTO saveDrug(DrugDTO drugDTO);

    /**
     * Partially updates a drug.
     *
     * @param drugDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DrugDTO> partialUpdateDrug(DrugDTO drugDTO);

    /**
     * Get all the drugs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DrugDTO> findAllDrug(Pageable pageable);

    /**
     * Get the "id" drug.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DrugDTO> findOneDrug(Long id);

    /**
     * Delete the "id" drug.
     *
     * @param id the id of the entity.
     */
    void deleteDrug(Long id);
}
