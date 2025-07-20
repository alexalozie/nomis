package org.nomisng.repository;

import org.nomisng.domain.entity.HouseholdMigration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseholdMigrationRepository extends JpaRepository<HouseholdMigration, Long>, JpaSpecificationExecutor {

    List<HouseholdMigration> findAllByHouseholdIdAndActive(Long householdId, int active);

    /*@Query(value = "SELECT count(household_id) FROM household_migration WHERE ward_id = ?1 ", nativeQuery = true)
    Long findByWardId(Long wardId);*/
}

