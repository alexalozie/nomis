package org.nomisng.repository;

import org.nomisng.domain.entity.Encounter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long>, JpaSpecificationExecutor {

    List<Encounter> findAllByArchived(int unArchived);

    Optional<Encounter> findByIdAndArchived(Long id, int archived);

    @Query(value = "SELECT * FROM encounter e " +
            "LEFT JOIN household_member h ON h.id = e.household_member_id " +
            "WHERE h.household_id =?1 AND h.archived=?2 AND e.archived=?3 ORDER BY e.id DESC;", nativeQuery = true)
    List<Encounter> findAllHouseholdMemberAndArchived(Long householdId, int unArchivedHouseHold, int unArchivedEncounter);

    Page<Encounter> findAllByIdAndFormCodeAndArchivedOrderByIdDesc(Long id, String formCode, int archived, Pageable pageable);

    Page<Encounter> findAllByHouseholdMemberIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(Long householdMemberId, String formCode, Long cboProjectId, int archived, Pageable pageable);

    Page<Encounter> findAllByHouseholdIdAndFormCodeAndCboProjectIdAndArchivedOrderByIdDesc(Long householdId, String formCode, Long cboProjectId, int archived, Pageable pageable);

    List<Encounter> findAllByHouseholdMemberIdAndArchived(Long householdId, int archived);

    Optional<Encounter> findTopByFormCodeAndHouseholdMemberIdAndArchivedOrderByIdAsc(String formCode, Long householdMemberId, int archived);

    List<Encounter> findAllByCboProjectIdAndArchived(Long cboProjectId, int archived);

    Optional<Encounter> findByIdAndCboProjectIdAndArchived(Long id, Long cboProjectId, int archived);

    @Query(value = "SELECT * FROM encounter " +
            "WHERE household_id = ?1 " +
            "AND form_code= ?2 " +
            "AND archived = ?3 " +
            "AND cbo_project_id = ?4 " +
            "AND date_encounter BETWEEN ?5 AND ?6 ORDER BY id DESC", nativeQuery = true)
    Page<Encounter> findAllByHouseholdIdAndFormCodeAndArchivedAndCboProjectIdAndDateEncounterOrderByIdDesc(Long householdId, String formCode , int archived,
                                                                                                           Long cboProjectId, LocalDate from,
                                                                                                           LocalDate to, Pageable pageable);


    @Query(value = "SELECT * FROM encounter " +
            "WHERE household_member_id = ?1 " +
            "AND form_code= ?2 " +
            "AND archived = ?3 " +
            "AND cbo_project_id = ?4 " +
            "AND date_encounter BETWEEN ?5 AND ?6 ORDER BY id DESC", nativeQuery = true)
    Page<Encounter> findAllByHouseholdMemberIdAndFormCodeAndArchivedAndCboProjectIdAndDateEncounterOrderByIdDesc(Long householdMemberId, String formCode , int archived,
                                                                                                           Long cboProjectId, LocalDate from,
                                                                                                           LocalDate to, Pageable pageable);

}

