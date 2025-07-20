package org.nomisng.repository;

import org.nomisng.domain.entity.Role;
import org.nomisng.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUserNameAndArchived(String userName, int archived);

    @EntityGraph(attributePaths = "role")
    Optional<User> findOneWithRoleByUserName(String userName);

    @EntityGraph(attributePaths = "role")
    Optional<User> findOneWithRoleByUserNameAndArchived(String userName, int archived);

    Page<User> findAll(Pageable pageable);

    List<User>findAllByRoleIn(HashSet<Role> roles);

    Optional<User> findByIdAndArchived(Long id, int archived);

    Page<User> findAllByArchived(int archived, Pageable pageable);

    @Query(value = "SELECT * FROM application_user WHERE archived=0 and id in " +
            "(select user_id from application_user_role au join role r " +
            " on au.role_id = r.id where name =?1)", nativeQuery = true)
    List<User>findUsersByRoleName(String roleName);
}
