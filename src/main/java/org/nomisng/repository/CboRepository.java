package org.nomisng.repository;

import org.nomisng.domain.entity.Cbo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CboRepository extends JpaRepository<Cbo, Long>, JpaSpecificationExecutor {

    Optional<Cbo> findByNameAndArchived(String name, int archived);

    List<Cbo> findAllByArchived(int archived);

    @Query(value = "SELECT id FROM cbo WHERE archived=0", nativeQuery = true)
    List<Long> findAllId();

    boolean existsByCode(String code);
    @Query(value = "SELECT distinct id, name, description, code, created_by, " +
            "date_created, modified_by, date_modified, archived FROM cbo " +
            "WHERE id in (select cbo_id from cbo_project " +
            "where id = ?1) and archived=?2", nativeQuery = true)
    Optional<Cbo> getCboByCboProjectId(Long cboProjectId, int archived);
}

