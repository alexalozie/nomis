package org.nomisng.repository;

import org.nomisng.domain.entity.FormFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormFlagRepository extends JpaRepository<FormFlag, Long> {

    List<FormFlag> findAllByArchived(int archived);

    Optional<FormFlag> findByFormCodeAndFlagIdAndStatusAndArchived(String formCode, Long flagId, Integer status, int archived);

    Optional<FormFlag> findByIdAndArchived(Long id, int archived);

    Optional<FormFlag> findByFlagIdAndFormCodeAndArchived(Long flagId, String formCode, int archived);

    List<FormFlag> findByFormCodeAndStatusAndArchived(String formCode, int status, int archived);

    void deleteByFlagId(Long flagId);
}
