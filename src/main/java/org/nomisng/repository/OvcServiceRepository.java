package org.nomisng.repository;

import org.nomisng.domain.entity.OvcService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OvcServiceRepository extends JpaRepository<OvcService, Long>, JpaSpecificationExecutor {

    Optional<OvcService> findProgramByCodeAndArchived(String Code, int archived);

    Optional<OvcService> findByIdAndArchived(Long id, int archived);

    List<OvcService> findAllByArchivedOrderByIdDesc(int archived);

    Optional<OvcService> findByNameAndServiceTypeAndArchived(String name, int serviceType, int archived);

    List<OvcService> findByArchived(int archived);
}

