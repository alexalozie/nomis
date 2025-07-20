package org.nomisng.repository;

import org.nomisng.domain.entity.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long>, JpaSpecificationExecutor {
    List findAllByArchivedOrderByIdDesc(int archived);

    //Order alphabetical Name
    List findAllByArchivedOrderByNameAsc(int archived);

    Optional<Domain> findByNameAndArchived(String name, int archived);

    Optional<Domain> findByIdAndArchived(Long id, int archived);

    Optional<Domain> findByCodeAndArchived(String domainCode, int archived);
}

