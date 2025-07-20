package org.nomisng.repository;

import org.nomisng.domain.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    Optional<School> findByName(String name);
    @Query(value = "SELECT name FROM school where name = ?1 and state=?2 and lga=?3", nativeQuery = true)
    Optional<School> getByNameAndStateIdAndLgaId(String name, Long stateId, Long lgaId);
}
