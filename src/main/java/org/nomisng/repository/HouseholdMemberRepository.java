package org.nomisng.repository;

import org.nomisng.domain.dto.MapDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.domain.entity.HouseholdMember;
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
public interface HouseholdMemberRepository extends JpaRepository<HouseholdMember, Long>, JpaSpecificationExecutor {

    Optional<HouseholdMember> findByIdAndArchived(Long id, int archived);

    @Query(value = "SELECT * FROM household_member h WHERE " +
            "h.household_id=?1 AND h.details ->>'firstName' ilike ?2  " +
            "AND h.details ->>'lastName' ilike ?3 AND h.archived=?4", nativeQuery = true)
    Optional<HouseholdMember> findByHouseholdIdAndFirstNameAndLastNameAndArchived(Long householdId, String firstName, String lastName, int archived);

    @Query(value = "SELECT * FROM household_member h WHERE " +
            "h.household_id=?1 AND h.unique_id=?2", nativeQuery = true)
    Optional<HouseholdMember> findByUniqueId(Long householdId, String uniqueId, int archived);

    Page<HouseholdMember> findAllByCboProjectIdAndArchivedOrderByIdDesc(Long cboProjectId, int archived, Pageable pageable);

    @Query(value = "SELECT * FROM household_member h " +
            "WHERE (h.details ->>'firstName' ilike ?1  " +
            "OR h.details ->>'lastName' ilike ?1 " +
            "OR h.details ->>'mobilePhoneNumber' ilike ?1) " +
            "OR h.unique_id ilike ?1 " +
            "AND cbo_project_id = ?2 AND h.archived=?3", nativeQuery = true)
    Page<HouseholdMember> findAllByCboProjectIdAndArchivedAndSearchParameterOrderByIdDesc(String search, Long cboProjectId, int archived, Pageable pageable);

    @Query(value = "SELECT Count(*) FROM household_member WHERE household_id = ?1", nativeQuery = true)
    Long findHouseholdMemberCountOfHousehold(Long householdId);

    List<HouseholdMember> findByHouseholdIdAndArchived(Long householdId, int archived);

    List<HouseholdMember> findByHouseholdIdAndHouseholdMemberTypeAndArchived(Long householdId, int memberType, int archived);

    //Summary
    @Query(value = "SELECT COUNT(DISTINCT unique_id) FROM household_member WHERE " +
            "household_member_type = 2 AND cbo_project_id = ?1 AND archived = 0", nativeQuery = true)
    Long getTotalVc(Long cboProjectId);

    @Query(value = "SELECT COUNT(DISTINCT unique_id) FROM household_member WHERE " +
            "details->>'date_of_enrolment' BETWEEN ?1 AND ?2 AND " +
            "household_member_type = 2 AND cbo_project_id = ?3 AND archived = 0", nativeQuery = true)
    Long getNewlyEnrolledByDateRange(LocalDate startDate, LocalDate endDate, Long cboProjectId);
    @Query(value = "SELECT count(distinct unique_id) FROM household_member h LEFT JOIN encounter e " +
            "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id " +
            "WHERE h.household_member_type = 2 AND e.date_encounter BETWEEN ?1 AND ?2 " +
            "AND (h.details->'hiv_test_result'->>'code' = 'e528faaf-7735-4cbc-a1b4-d110247b7227' " +
            "OR (cast(h.details->'enrolmentStreams'->>'enrolment_stream_number' as integer)  = 1) " +
            "OR f.data->'new_hiv_test_result'->>'code' = 'efb47f13-7760-4483-99f4-5f525ccd1fcd') " +
            "AND e.cbo_project_id = ?3 AND e.archived = 0 ", nativeQuery = true)
    Long getPositiveByDateRange(LocalDate startDate, LocalDate endDate, Long cboProjectId);

    @Query(value = "SELECT count(distinct unique_id) FROM household_member h LEFT JOIN encounter e " +
            "ON e.household_member_id = h.id LEFT JOIN form_data f ON f.encounter_id = e.id " +
            "WHERE h.household_member_type = 2 " +
            "AND (h.details->'hiv_test_result'->>'code' = 'e528faaf-7735-4cbc-a1b4-d110247b7227' " +
            "OR (cast(h.details->'enrolmentStreams'->>'enrolment_stream_number' as integer)  = 1) " +
            "OR f.data->'new_hiv_test_result'->>'code' = 'efb47f13-7760-4483-99f4-5f525ccd1fcd') " +
            "AND e.cbo_project_id = ?1 AND e.archived = 0 ", nativeQuery = true)
    Long getTotalPositive(Long cboProjectId);

    @Query(value = "SELECT COUNT(DISTINCT e.household_member_id) count_linked FROM " +
            "encounter e JOIN household_member h ON e.household_member_id = h.id " +
            "JOIN form_data f on f.encounter_id = e.id " +
            "WHERE h.household_member_type = 2 AND (lower(h.details->'enrolledOnART'->>'display') = 'yes' " +
            "OR f.data->>'currentlyOnART' = 'yes') AND (f.data->>'art_uid_no' is not null OR " +
            "h.details->>'art_unique_id' is not null) AND e.cbo_project_id = ?1 AND e.archived = 0", nativeQuery = true)
    Long getTotalLinked(Long cboProjectId);

    @Query(value = "SELECT COUNT(DISTINCT e.household_member_id) count_linked FROM " +
            "encounter e JOIN household_member h ON e.household_member_id = h.id " +
            "JOIN form_data f on f.encounter_id = e.id " +
            "WHERE h.household_member_type = 2 AND (lower(h.details->'enrolledOnART'->>'display') = 'yes' " +
            "OR f.data->>'currentlyOnART' = 'yes') AND (f.data->>'art_uid_no' is not null OR " +
            "h.details->>'art_unique_id' is not null) AND e.date_encounter BETWEEN ?1 AND ?2 " +
            "AND e.cbo_project_id = ?3 AND e.archived = 0", nativeQuery = true)
    Long getLinkedByDateRange(LocalDate startDate, LocalDate endDate, Long cboProjectId);

    @Query(value = "SELECT * FROM household_member WHERE unique_id = ?1 and archived = 0", nativeQuery = true)
    Optional<HouseholdMember> findByUniqueId(String uniqueId);

    @Query(value = "select unique_id from household_member where household_id =?1 " +
            "order by date_modified desc", nativeQuery = true)
    Optional<String> getLastUniqueId(Long householdId);

}

