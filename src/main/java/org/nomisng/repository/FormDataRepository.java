package org.nomisng.repository;

import org.nomisng.domain.entity.FormData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface FormDataRepository extends JpaRepository<FormData, Long>, JpaSpecificationExecutor {
    Optional<FormData> findByIdAndCboProjectIdAndArchived(Long id, Long cboProjectId, int archived);

    //Optional<FormData> findByIdAndArchived(Long id, int archived, Long organisationUnitId);

    List<FormData> findAllByArchivedAndCboProjectIdOrderByIdDesc(int archived, Long cboProjectId);

    Optional<FormData> findOneByEncounterIdOrderByIdDesc(Long id);

    @Query(value = "SELECT COUNT(*) FROM form_data f LEFT JOIN encounter e ON e.id=f.id " +
            "WHERE f.cbo_project_id = ?1 AND e.archived = 0 AND e.date_encounter BETWEEN ?2 AND ?3 ", nativeQuery = true)
    Object countAllByCboProjectIdAndDateBetween(Long cboProjectId, LocalDate startDate, LocalDate endDate);

    //List<FormData> findAllByEncounterIdAndArchivedAndOrganisationUnitIdOrderByIdDesc(Long encounterId, int archived, Long organisationUnitId);

}

