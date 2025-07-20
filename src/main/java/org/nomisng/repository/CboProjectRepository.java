package org.nomisng.repository;

import org.nomisng.domain.entity.CboProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CboProjectRepository extends JpaRepository<CboProject, Long>, JpaSpecificationExecutor {

    List<CboProject> findAllByCboIdAndDonorIdAndArchived(Long cboId, Long donorId, int archived);

    Optional<CboProject> findByCboIdAndDonorIdAndImplementerIdAndArchived(Long cboId, Long donorId, Long implementer, int archived);

    Optional<CboProject> findTopByCboIdAndDonorIdAndImplementerIdAndArchivedOrderByIdDesc(Long cboId, Long donorId, Long implementer, int archived);


    List<CboProject> findAllByArchived(int archived);

    @Query(value = "SELECT * FROM cbo_project WHERE cbo_id IN (?1) " +
            "AND donor_id IN (?2) AND implementer_id IN (?3) " +
            "AND archived=0 ORDER BY id DESC", nativeQuery = true)
    Page<CboProject> findAllCboProjects(List<Long> cboId, List<Long> donorId,
                                                                List<Long> implementerId, Pageable pageable);

    List<CboProject> findAllByCboIdAndArchived(Long cboId, int archived);
    Optional<CboProject> findByIdAndArchived(Long id, int archived);

    /*@Query(value = "SELECT id, description FROM cbo_project WHERE archived=0", nativeQuery = true)
    List getAllCboProjectIdAndDescription();*/
}

