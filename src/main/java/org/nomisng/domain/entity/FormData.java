package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "form_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormData extends JsonBEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "encounter_id")
    private Long encounterId;
    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data", nullable = false, columnDefinition = "jsonb")
    private Object data;
    @Basic
    @Column(name = "cbo_project_id")
    @JsonIgnore
    private Long cboProjectId;
    @Basic
    @Column(name = "archived")
    private int archived;
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private String createdBy = "guest@nomisng.org";
    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now();
    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    private String modifiedBy = "guest@nomisng.org";
    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    private LocalDateTime dateModified = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "encounter_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    public Encounter encounterByEncounterId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    public Encounter cboProjectByCboProjectId;

}
