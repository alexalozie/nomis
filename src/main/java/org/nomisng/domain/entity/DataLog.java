package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "data_log")
@AllArgsConstructor
@NoArgsConstructor
public class DataLog implements Serializable, Persistable<UUID> {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    @Id
    private UUID id;
    private String identifier;
    private String status;
    @NonNull
    @Column(name = "date_modified")
    private LocalDateTime dateModified = LocalDateTime.now();

    @Override
    public boolean isNew() {
        return id == null;
    }
}
