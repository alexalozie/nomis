package org.nomisng.repository;


import org.nomisng.domain.entity.RegimenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegimenTypeRepository extends JpaRepository<RegimenType, Long> {
    @Query("SELECT r FROM RegimenType r WHERE r.id = 1 OR r.id = 3 ORDER BY r.description")
    List<RegimenType> firstLineRegimentType();

    @Query("SELECT r FROM RegimenType r WHERE r.id = 2 OR r.id = 4 ORDER BY  r.description")
    List<RegimenType> secondLineRegimentType();

    @Query("SELECT r FROM RegimenType r WHERE r.id <= 4 OR r.id= 14 ORDER BY r.description")
    List<RegimenType> commenceRegimentType();

    Optional<RegimenType> findOptionalByDescription(String description);
}
