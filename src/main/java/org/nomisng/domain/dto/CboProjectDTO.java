package org.nomisng.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CboProjectDTO {
    private Long id;
    @NotNull(message = "cboId is mandatory")
    private Long cboId;
    @NotNull(message = "donorId is mandatory")
    private Long donorId;
    @NotNull(message = "implementerId is mandatory")
    private Long implementerId;
    @NotNull(message = "organisationUnitIds is mandatory")
    private List<Long> organisationUnitIds;
    private String cboName;
    private String donorName;
    private String implementerName;
    private String organisationUnitName;
    @NotBlank(message = "description is mandatory")
    private String description;
    private List organisationUnits;
    private int archived;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public CboProjectDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public Long getCboId() {
        return this.cboId;
    }

    @SuppressWarnings("all")
    public Long getDonorId() {
        return this.donorId;
    }

    @SuppressWarnings("all")
    public Long getImplementerId() {
        return this.implementerId;
    }

    @SuppressWarnings("all")
    public List<Long> getOrganisationUnitIds() {
        return this.organisationUnitIds;
    }

    @SuppressWarnings("all")
    public String getCboName() {
        return this.cboName;
    }

    @SuppressWarnings("all")
    public String getDonorName() {
        return this.donorName;
    }

    @SuppressWarnings("all")
    public String getImplementerName() {
        return this.implementerName;
    }

    @SuppressWarnings("all")
    public String getOrganisationUnitName() {
        return this.organisationUnitName;
    }

    @SuppressWarnings("all")
    public String getDescription() {
        return this.description;
    }

    @SuppressWarnings("all")
    public List getOrganisationUnits() {
        return this.organisationUnits;
    }

    @SuppressWarnings("all")
    public int getArchived() {
        return this.archived;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setCboId(final Long cboId) {
        this.cboId = cboId;
    }

    @SuppressWarnings("all")
    public void setDonorId(final Long donorId) {
        this.donorId = donorId;
    }

    @SuppressWarnings("all")
    public void setImplementerId(final Long implementerId) {
        this.implementerId = implementerId;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnitIds(final List<Long> organisationUnitIds) {
        this.organisationUnitIds = organisationUnitIds;
    }

    @SuppressWarnings("all")
    public void setCboName(final String cboName) {
        this.cboName = cboName;
    }

    @SuppressWarnings("all")
    public void setDonorName(final String donorName) {
        this.donorName = donorName;
    }

    @SuppressWarnings("all")
    public void setImplementerName(final String implementerName) {
        this.implementerName = implementerName;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnitName(final String organisationUnitName) {
        this.organisationUnitName = organisationUnitName;
    }

    @SuppressWarnings("all")
    public void setDescription(final String description) {
        this.description = description;
    }

    @SuppressWarnings("all")
    public void setOrganisationUnits(final List organisationUnits) {
        this.organisationUnits = organisationUnits;
    }

    @SuppressWarnings("all")
    public void setArchived(final int archived) {
        this.archived = archived;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CboProjectDTO)) return false;
        final CboProjectDTO other = (CboProjectDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArchived() != other.getArchived()) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$cboId = this.getCboId();
        final Object other$cboId = other.getCboId();
        if (this$cboId == null ? other$cboId != null : !this$cboId.equals(other$cboId)) return false;
        final Object this$donorId = this.getDonorId();
        final Object other$donorId = other.getDonorId();
        if (this$donorId == null ? other$donorId != null : !this$donorId.equals(other$donorId)) return false;
        final Object this$implementerId = this.getImplementerId();
        final Object other$implementerId = other.getImplementerId();
        if (this$implementerId == null ? other$implementerId != null : !this$implementerId.equals(other$implementerId)) return false;
        final Object this$organisationUnitIds = this.getOrganisationUnitIds();
        final Object other$organisationUnitIds = other.getOrganisationUnitIds();
        if (this$organisationUnitIds == null ? other$organisationUnitIds != null : !this$organisationUnitIds.equals(other$organisationUnitIds)) return false;
        final Object this$cboName = this.getCboName();
        final Object other$cboName = other.getCboName();
        if (this$cboName == null ? other$cboName != null : !this$cboName.equals(other$cboName)) return false;
        final Object this$donorName = this.getDonorName();
        final Object other$donorName = other.getDonorName();
        if (this$donorName == null ? other$donorName != null : !this$donorName.equals(other$donorName)) return false;
        final Object this$implementerName = this.getImplementerName();
        final Object other$implementerName = other.getImplementerName();
        if (this$implementerName == null ? other$implementerName != null : !this$implementerName.equals(other$implementerName)) return false;
        final Object this$organisationUnitName = this.getOrganisationUnitName();
        final Object other$organisationUnitName = other.getOrganisationUnitName();
        if (this$organisationUnitName == null ? other$organisationUnitName != null : !this$organisationUnitName.equals(other$organisationUnitName)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$organisationUnits = this.getOrganisationUnits();
        final Object other$organisationUnits = other.getOrganisationUnits();
        if (this$organisationUnits == null ? other$organisationUnits != null : !this$organisationUnits.equals(other$organisationUnits)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof CboProjectDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArchived();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $cboId = this.getCboId();
        result = result * PRIME + ($cboId == null ? 43 : $cboId.hashCode());
        final Object $donorId = this.getDonorId();
        result = result * PRIME + ($donorId == null ? 43 : $donorId.hashCode());
        final Object $implementerId = this.getImplementerId();
        result = result * PRIME + ($implementerId == null ? 43 : $implementerId.hashCode());
        final Object $organisationUnitIds = this.getOrganisationUnitIds();
        result = result * PRIME + ($organisationUnitIds == null ? 43 : $organisationUnitIds.hashCode());
        final Object $cboName = this.getCboName();
        result = result * PRIME + ($cboName == null ? 43 : $cboName.hashCode());
        final Object $donorName = this.getDonorName();
        result = result * PRIME + ($donorName == null ? 43 : $donorName.hashCode());
        final Object $implementerName = this.getImplementerName();
        result = result * PRIME + ($implementerName == null ? 43 : $implementerName.hashCode());
        final Object $organisationUnitName = this.getOrganisationUnitName();
        result = result * PRIME + ($organisationUnitName == null ? 43 : $organisationUnitName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $organisationUnits = this.getOrganisationUnits();
        result = result * PRIME + ($organisationUnits == null ? 43 : $organisationUnits.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "CboProjectDTO(id=" + this.getId() + ", cboId=" + this.getCboId() + ", donorId=" + this.getDonorId() + ", implementerId=" + this.getImplementerId() + ", organisationUnitIds=" + this.getOrganisationUnitIds() + ", cboName=" + this.getCboName() + ", donorName=" + this.getDonorName() + ", implementerName=" + this.getImplementerName() + ", organisationUnitName=" + this.getOrganisationUnitName() + ", description=" + this.getDescription() + ", organisationUnits=" + this.getOrganisationUnits() + ", archived=" + this.getArchived() + ")";
    }
    //</editor-fold>
}
