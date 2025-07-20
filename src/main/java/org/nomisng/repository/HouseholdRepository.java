package org.nomisng.repository;

import org.nomisng.domain.entity.Household;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Long>, JpaSpecificationExecutor {
    @Query(value = "SELECT * FROM household WHERE id = ?1 AND archived = ?2 " +
            "AND organisation_unit_id IN (?3) ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<Household> findByIdAndArchived(Long id, int archived, List<Long> organisationUnitIds);

    Optional<Household> findByIdAndArchived(Long id, int archived);

    List<Household> findAllByArchivedOrderByIdAsc(int archived);

    @Query(value = "SELECT * FROM household WHERE archived = ?1 " +
            "AND organisation_unit_id IN (?2) ORDER BY id DESC", nativeQuery = true)
    List<Household> findAllByArchivedOrderByIdDesc(int archived, List<Long> organisationUnitIds);

    Page<Household> findByCboProjectIdAndArchivedOrderByIdDesc(Long cboProjectId, int archived, Pageable pageable);

    Optional<Household> findByIdAndCboProjectIdAndArchived(Long id, Long cboProjectId, int archived);

    @Query(value = "SELECT * FROM household h WHERE (h.details ->>'lga' ilike ?1 " +
            "OR h.details ->>'ward' ilike ?1 OR h.details ->>'state' ilike ?1 " +
            "OR h.details ->>'community' ilike ?1 OR h.details ->>'uniqueId' ilike ?1) " +
            "AND h.cbo_project_id = ?2 AND archived = ?3", nativeQuery = true)
    Page<Household> findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(String search, Long cboProjectId,
                                                                                    int archived, Pageable pageable);

    @Query(value = "SELECT max(serial_number) FROM household WHERE ward_id = ?1", nativeQuery = true)
    Optional<Long> findMaxSerialNumber(Long wardId);


    //Summary
    @Query(value = "SELECT COUNT(DISTINCT unique_id) FROM household WHERE cbo_project_id = ?1 and archived = 0",
        nativeQuery = true)
    Long getTotalHousehold(Long cboProjectId);

    @Query(value = "SELECT COUNT(DISTINCT e.household_id) " +
            "FROM encounter e JOIN form_data f ON e.id = f.encounter_id " +
            "WHERE e.form_code = '6fb50a2e-e2d3-4534-a641-1abdb75c6fda' AND " +
            "f.data->'beneficiary_status'->>'code' = '9b39b8bd-8cd8-41cd-8bc7-1bb4491ab102' " +
            "AND e.cbo_project_id = ?1 AND e.archived = 0", nativeQuery = true)
    Long getTotalGraduated(Long cboProjectId);

    @Query(value = "SELECT * FROM household WHERE unique_id = ?1 and archived = 0", nativeQuery = true)
    Optional<Household> findByUniqueId(String uniqueId);

}

