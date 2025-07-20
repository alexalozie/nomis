package org.nomisng.repository;


import org.nomisng.domain.entity.ApplicationCodeset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationCodesetRepository extends JpaRepository<ApplicationCodeset, Long>, JpaSpecificationExecutor {

    Optional<ApplicationCodeset> findByDisplayAndCodesetGroup(String display, String codesetGroup);

    List<ApplicationCodeset> findAllByCodesetGroupAndArchivedOrderByIdAsc(String codesetGroup, int archived);

    ApplicationCodeset findByDisplay(String display);

    Boolean existsByDisplayAndCodesetGroup(String display, String codesetGroup);

    Optional<ApplicationCodeset> findByDisplayAndCodesetGroupAndArchived(String display, String codesetGroup, Integer active);

    Optional<ApplicationCodeset> findByIdAndArchived(Long id, int archive);

    List<ApplicationCodeset> findAllByArchivedOrderByIdAsc(int archived);

    @Query(value = "SELECT * FROM application_codeset WHERE date_modified >= :lastUpdate", nativeQuery = true)
    List<ApplicationCodeset> findByDateModified(@Param(value="lastUpdate") LocalDateTime lastUpdate);
}
