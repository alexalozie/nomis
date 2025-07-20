package org.nomisng.repository;


import org.nomisng.domain.entity.Regimen;
import org.nomisng.domain.entity.RegimenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RegimenRepository extends JpaRepository<Regimen, Long> {
    Optional<Regimen> findByDescription(String description);

    List<Regimen> findByRegimenType(RegimenType regimenType);

    List<Regimen> findByRegimenTypeIn(List<RegimenType> regimenTypes);

    @Query("SELECT distinct r FROM Regimen r join r.regimenType t WHERE t.description = ?1 ORDER BY r.description")
    List<Regimen> retrieveRegimenByName(RegimenType regimenType);

    List<Regimen> findByRegimenTypeAndActiveTrueOrderByPriorityDesc(RegimenType regimenType);
}
