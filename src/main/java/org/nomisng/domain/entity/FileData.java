package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Table(name = "file_data")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class FileData  implements Serializable, Persistable<UUID> {
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uid", updatable = false, nullable = false)
    @Id
    private UUID id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;

    @Lob
    private byte[] data;

    public FileData(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @Override
    public boolean isNew() {
        return id == null;
    }
}
