package org.nomisng.repository;

import org.nomisng.domain.entity.Implementer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImplementerRepository extends JpaRepository<Implementer, Long>, JpaSpecificationExecutor {

    Optional<Implementer> findByNameAndArchived(String name, int archived);

    @Query(value = "SELECT id FROM implementer WHERE archived=0", nativeQuery = true)
    List<Long> findAllId();
}

