package org.nomisng.repository;

import org.nomisng.domain.entity.DataLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface DataLogRepository extends JpaRepository<DataLog, UUID> {
    DataLog findByIdentifier(String identifier);

    DataLog findByDateModified(LocalDateTime lastUpdate);
}
