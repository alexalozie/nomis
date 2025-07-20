package org.nomisng.repository;

import org.nomisng.domain.entity.Donor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonorRepository extends JpaRepository<Donor, Long>, JpaSpecificationExecutor {

    Optional<Donor> findByNameAndArchived(String name, int archived);

    @Query(value = "SELECT id FROM donor WHERE archived=0", nativeQuery = true)
    List<Long> findAllId();

    List<Donor> findAllByArchivedOrderByIdDesc(int archived);
}

