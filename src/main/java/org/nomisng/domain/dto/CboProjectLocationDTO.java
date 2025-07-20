package org.nomisng.domain.dto;

import javax.validation.constraints.NotNull;

public class CboProjectLocationDTO {
    private Long id;
    @NotNull(message = "cboProjectId is mandatory")
    private Long cboProjectId;
    private int archived;
    @NotNull(message = "organisationUnitId is mandatory")
    private Long organisationUnitId;
    private String organisationUnitName;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public CboProjectLocationDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public Long getCboProjectId() {
        return this.cboProjectId;
    }

    @SuppressWarnings("all")
    public int getArchived() {
        return this.archived;
    }

    @SuppressWarnings("all")
    public Long getOrganisationUnitId() {
        return this.organisationUnitId;
    }

    @SuppressWarnings("all")
    public String getOrganisationUnitName() {
        return this.organisationUnitName;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setCboProjectId(final Long cboProjectId) {
        this.cboProjectId = cboProjectId;
    }

    @SuppressWarnings("all")
    public void setArchived(final int archived) {
        this.archived = archived;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnitId(final Long organisationUnitId) {
        this.organisationUnitId = organisationUnitId;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnitName(final String organisationUnitName) {
        this.organisationUnitName = organisationUnitName;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CboProjectLocationDTO)) return false;
        final CboProjectLocationDTO other = (CboProjectLocationDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArchived() != other.getArchived()) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$cboProjectId = this.getCboProjectId();
        final Object other$cboProjectId = other.getCboProjectId();
        if (this$cboProjectId == null ? other$cboProjectId != null : !this$cboProjectId.equals(other$cboProjectId)) return false;
        final Object this$organisationUnitId = this.getOrganisationUnitId();
        final Object other$organisationUnitId = other.getOrganisationUnitId();
        if (this$organisationUnitId == null ? other$organisationUnitId != null : !this$organisationUnitId.equals(other$organisationUnitId)) return false;
        final Object this$organisationUnitName = this.getOrganisationUnitName();
        final Object other$organisationUnitName = other.getOrganisationUnitName();
        if (this$organisationUnitName == null ? other$organisationUnitName != null : !this$organisationUnitName.equals(other$organisationUnitName)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof CboProjectLocationDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArchived();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $cboProjectId = this.getCboProjectId();
        result = result * PRIME + ($cboProjectId == null ? 43 : $cboProjectId.hashCode());
        final Object $organisationUnitId = this.getOrganisationUnitId();
        result = result * PRIME + ($organisationUnitId == null ? 43 : $organisationUnitId.hashCode());
        final Object $organisationUnitName = this.getOrganisationUnitName();
        result = result * PRIME + ($organisationUnitName == null ? 43 : $organisationUnitName.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "CboProjectLocationDTO(id=" + this.getId() + ", cboProjectId=" + this.getCboProjectId() + ", archived=" + this.getArchived() + ", organisationUnitId=" + this.getOrganisationUnitId() + ", organisationUnitName=" + this.getOrganisationUnitName() + ")";
    }
    //</editor-fold>
}
