package org.nomisng.repository;

import org.nomisng.domain.entity.ApplicationUserCboProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ApplicationUserCboProjectRepository extends JpaRepository<ApplicationUserCboProject, Long>, JpaSpecificationExecutor {
    Optional<ApplicationUserCboProject> findByApplicationUserIdAndCboProjectId(Long userId, Long id);

    Optional<ApplicationUserCboProject> findByApplicationUserIdAndCboProjectIdAndArchived(Long userId, Long id, int archived);

    @Query(value = "SELECT * FROM application_user_cbo_project WHERE application_user_id = ?1 " +
            "AND archived = ?2 " +
            "AND cbo_project_id IN (?3)", nativeQuery = true)
    List<ApplicationUserCboProject> findAllByApplicationUserIdAndCboProjectIdAndArchived(Long userId,
                                                                                         int archived,
                                                                                         List<Long> cboProjectIds);

    @Query(value = "SELECT cbo_project_id FROM application_user_cbo_project WHERE archived = ?1 ", nativeQuery = true)
    List<Long> findAllCboProjectIdByArchived(int archived);

    List<ApplicationUserCboProject> findAllByApplicationUserIdOrderByIdAsc(Long userId);

    List<ApplicationUserCboProject> findAllByApplicationUserIdAndArchivedOrderByIdAsc(Long applicationUserId, int archived);

    void deleteByApplicationUserId(Long id);
}

