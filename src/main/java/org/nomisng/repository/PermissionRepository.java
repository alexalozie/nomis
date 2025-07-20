package org.nomisng.repository;

import org.nomisng.domain.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor {
    Optional<Permission> findByNameAndArchived(String name, int archived);

    @Query(value = "SELECT * FROM permission where name ilike ?1 AND archived=0", nativeQuery = true)
    List<Permission> findAllByNameIsLike(String name);

    List<Permission> findByNameNotIn(List<String> name);

    List<Permission> findAllByArchived(int archived);

    Optional<Permission> findByDescriptionAndArchived(String description, int archived);

}

