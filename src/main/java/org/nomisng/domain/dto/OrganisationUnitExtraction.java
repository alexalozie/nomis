package org.nomisng.domain.dto;

public class OrganisationUnitExtraction {
    private String organisationUnitName;
    private String parentOrganisationUnitName;
    private String parentParentOrganisationUnitName;
    private Long parentOrganisationUnitId;
    private String description;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public OrganisationUnitExtraction() {
    }

    @SuppressWarnings("all")
    public String getOrganisationUnitName() {
        return this.organisationUnitName;
    }

    @SuppressWarnings("all")
    public String getParentOrganisationUnitName() {
        return this.parentOrganisationUnitName;
    }

    @SuppressWarnings("all")
    public String getParentParentOrganisationUnitName() {
        return this.parentParentOrganisationUnitName;
    }

    @SuppressWarnings("all")
    public Long getParentOrganisationUnitId() {
        return this.parentOrganisationUnitId;
    }

    @SuppressWarnings("all")
    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnitName(final String organisationUnitName) {
        this.organisationUnitName = organisationUnitName;
    }

    @SuppressWarnings("all")
    public void setParentOrganisationUnitName(final String parentOrganisationUnitName) {
        this.parentOrganisationUnitName = parentOrganisationUnitName;
    }

    @SuppressWarnings("all")
    public void setParentParentOrganisationUnitName(final String parentParentOrganisationUnitName) {
        this.parentParentOrganisationUnitName = parentParentOrganisationUnitName;
    }

    @SuppressWarnings("all")
    public void setParentOrganisationUnitId(final Long parentOrganisationUnitId) {
        this.parentOrganisationUnitId = parentOrganisationUnitId;
    }

    @SuppressWarnings("all")
    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrganisationUnitExtraction)) return false;
        final OrganisationUnitExtraction other = (OrganisationUnitExtraction) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$parentOrganisationUnitId = this.getParentOrganisationUnitId();
        final Object other$parentOrganisationUnitId = other.getParentOrganisationUnitId();
        if (this$parentOrganisationUnitId == null ? other$parentOrganisationUnitId != null : !this$parentOrganisationUnitId.equals(other$parentOrganisationUnitId)) return false;
        final Object this$organisationUnitName = this.getOrganisationUnitName();
        final Object other$organisationUnitName = other.getOrganisationUnitName();
        if (this$organisationUnitName == null ? other$organisationUnitName != null : !this$organisationUnitName.equals(other$organisationUnitName)) return false;
        final Object this$parentOrganisationUnitName = this.getParentOrganisationUnitName();
        final Object other$parentOrganisationUnitName = other.getParentOrganisationUnitName();
        if (this$parentOrganisationUnitName == null ? other$parentOrganisationUnitName != null : !this$parentOrganisationUnitName.equals(other$parentOrganisationUnitName)) return false;
        final Object this$parentParentOrganisationUnitName = this.getParentParentOrganisationUnitName();
        final Object other$parentParentOrganisationUnitName = other.getParentParentOrganisationUnitName();
        if (this$parentParentOrganisationUnitName == null ? other$parentParentOrganisationUnitName != null : !this$parentParentOrganisationUnitName.equals(other$parentParentOrganisationUnitName)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof OrganisationUnitExtraction;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $parentOrganisationUnitId = this.getParentOrganisationUnitId();
        result = result * PRIME + ($parentOrganisationUnitId == null ? 43 : $parentOrganisationUnitId.hashCode());
        final Object $organisationUnitName = this.getOrganisationUnitName();
        result = result * PRIME + ($organisationUnitName == null ? 43 : $organisationUnitName.hashCode());
        final Object $parentOrganisationUnitName = this.getParentOrganisationUnitName();
        result = result * PRIME + ($parentOrganisationUnitName == null ? 43 : $parentOrganisationUnitName.hashCode());
        final Object $parentParentOrganisationUnitName = this.getParentParentOrganisationUnitName();
        result = result * PRIME + ($parentParentOrganisationUnitName == null ? 43 : $parentParentOrganisationUnitName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "OrganisationUnitExtraction(organisationUnitName=" + this.getOrganisationUnitName() + ", parentOrganisationUnitName=" + this.getParentOrganisationUnitName() + ", parentParentOrganisationUnitName=" + this.getParentParentOrganisationUnitName() + ", parentOrganisationUnitId=" + this.getParentOrganisationUnitId() + ", description=" + this.getDescription() + ")";
    }
    //</editor-fold>
}
