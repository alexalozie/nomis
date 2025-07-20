package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "standard_codeset", schema = "public", catalog = "nomis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardCodeset {
    private Long id;
    private String code;
    private String description;
    private Long standardCodesetSourceId;
    private Integer archived;
    private String createdBy;
    private Timestamp dateCreated;
    private Timestamp dateModified;
    private String modifiedBy;
    private Collection<ApplicationCodesetStandardCodeset> applicationCodesetStandardCodesetsById;
    private StandardCodesetSource standardCodesetSourceByStandardCodesetSourceId;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "standard_codeset_source_id")
    public Long getStandardCodesetSourceId() {
        return standardCodesetSourceId;
    }

    public void setStandardCodesetSourceId(Long standardCodesetSourceId) {
        this.standardCodesetSourceId = standardCodesetSourceId;
    }

    @Basic
    @Column(name = "archived")
    public Integer getArchived() {
        return archived;
    }

    public void setArchived(Integer archived) {
        this.archived = archived;
    }

    @Basic
    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "date_created")
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Basic
    @Column(name = "date_modified")
    public Timestamp getDateModified() {
        return dateModified;
    }

    public void setDateModified(Timestamp dateModified) {
        this.dateModified = dateModified;
    }

    @Basic
    @Column(name = "modified_by")
    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StandardCodeset that = (StandardCodeset) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(code, that.code) &&
                Objects.equals(description, that.description) &&
                Objects.equals(standardCodesetSourceId, that.standardCodesetSourceId) &&
                Objects.equals(archived, that.archived) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(dateCreated, that.dateCreated) &&
                Objects.equals(dateModified, that.dateModified) &&
                Objects.equals(modifiedBy, that.modifiedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, description, standardCodesetSourceId, archived, createdBy, dateCreated, dateModified, modifiedBy);
    }

    @OneToMany(mappedBy = "standardCodesetByStandardCodesetId")
    public Collection<ApplicationCodesetStandardCodeset> getApplicationCodesetStandardCodesetsById() {
        return applicationCodesetStandardCodesetsById;
    }

    public void setApplicationCodesetStandardCodesetsById(Collection<ApplicationCodesetStandardCodeset> applicationCodesetStandardCodesetsById) {
        this.applicationCodesetStandardCodesetsById = applicationCodesetStandardCodesetsById;
    }

    @ManyToOne
    @JoinColumn(name = "standard_codeset_source_id", referencedColumnName = "id", insertable = false, updatable = false)
    public StandardCodesetSource getStandardCodesetSourceByStandardCodesetSourceId() {
        return standardCodesetSourceByStandardCodesetSourceId;
    }

    public void setStandardCodesetSourceByStandardCodesetSourceId(StandardCodesetSource standardCodesetSourceByStandardCodesetSourceId) {
        this.standardCodesetSourceByStandardCodesetSourceId = standardCodesetSourceByStandardCodesetSourceId;
    }
}
