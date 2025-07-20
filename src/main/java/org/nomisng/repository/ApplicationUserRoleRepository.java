package org.nomisng.repository;

import org.nomisng.domain.entity.ApplicationUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationUserRoleRepository extends JpaRepository<ApplicationUserRole, Long>, JpaSpecificationExecutor {
    List<ApplicationUserRole> findAllByUserId(Long id);

    void deleteByUserId(Long userId);
}

