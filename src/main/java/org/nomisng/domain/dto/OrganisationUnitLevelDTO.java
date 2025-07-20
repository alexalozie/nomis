package org.nomisng.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrganisationUnitLevelDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    private String description;
    @NotNull(message = "status is mandatory")
    private Integer status;
    @NotNull(message = "parentOrganisationUnitLevelId is mandatory")
    private Long parentOrganisationUnitLevelId;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public OrganisationUnitLevelDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("all")
    public Integer getStatus() {
        return this.status;
    }

    @SuppressWarnings("all")
    public Long getParentOrganisationUnitLevelId() {
        return this.parentOrganisationUnitLevelId;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setDescription(final String description) {
        this.description = description;
    }

    @SuppressWarnings("all")
    public void setStatus(final Integer status) {
        this.status = status;
    }

    @SuppressWarnings("all")
    public void setParentOrganisationUnitLevelId(final Long parentOrganisationUnitLevelId) {
        this.parentOrganisationUnitLevelId = parentOrganisationUnitLevelId;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrganisationUnitLevelDTO)) return false;
        final OrganisationUnitLevelDTO other = (OrganisationUnitLevelDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$parentOrganisationUnitLevelId = this.getParentOrganisationUnitLevelId();
        final Object other$parentOrganisationUnitLevelId = other.getParentOrganisationUnitLevelId();
        if (this$parentOrganisationUnitLevelId == null ? other$parentOrganisationUnitLevelId != null : !this$parentOrganisationUnitLevelId.equals(other$parentOrganisationUnitLevelId)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof OrganisationUnitLevelDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $parentOrganisationUnitLevelId = this.getParentOrganisationUnitLevelId();
        result = result * PRIME + ($parentOrganisationUnitLevelId == null ? 43 : $parentOrganisationUnitLevelId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "OrganisationUnitLevelDTO(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", status=" + this.getStatus() + ", parentOrganisationUnitLevelId=" + this.getParentOrganisationUnitLevelId() + ")";
    }
    //</editor-fold>
}
