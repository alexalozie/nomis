package org.nomisng.repository;

import org.nomisng.domain.entity.ReportInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReportInfoRepository extends JpaRepository<ReportInfo, Long>, JpaSpecificationExecutor {
    Optional<ReportInfo> findByName(String name);
    List<ReportInfo> findAllByArchived(int archived);

    Optional<ReportInfo> findByNameAndArchived(String name, int archived);

    Optional<ReportInfo> findByIdAndArchived(Long id, int archived);
    @Query(value = "SELECT * FROM report_info WHERE date_modified >= :lastUpdate", nativeQuery = true)
    List<ReportInfo> findByDateModified(LocalDateTime lastUpdate);
}
