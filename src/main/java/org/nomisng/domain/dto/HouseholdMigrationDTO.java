package org.nomisng.domain.dto;

import javax.validation.constraints.NotNull;

public class HouseholdMigrationDTO {
    private Long id;
    private String zipCode;
    private String city;
    private String street;
    private String landmark;
/*    @NotNull(message = "countryId is mandatory")
    private Long countryId;

    @NotNull(message = "stateId is mandatory")
    private Long stateId;

    private Long provinceId;

    private Long wardId;*/
    @NotNull(message = "householdId is mandatory")
    private Long householdId;
    private Integer active;
    private int archived;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public HouseholdMigrationDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public String getZipCode() {
        return this.zipCode;
    }

    @SuppressWarnings("all")
    public String getCity() {
        return this.city;
    }

    @SuppressWarnings("all")
    public String getStreet() {
        return this.street;
    }

    @SuppressWarnings("all")
    public String getLandmark() {
        return this.landmark;
    }

    @SuppressWarnings("all")
    public Long getHouseholdId() {
        return this.householdId;
    }

    @SuppressWarnings("all")
    public Integer getActive() {
        return this.active;
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
    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    @SuppressWarnings("all")
    public void setCity(final String city) {
        this.city = city;
    }

    @SuppressWarnings("all")
    public void setStreet(final String street) {
        this.street = street;
    }

    @SuppressWarnings("all")
    public void setLandmark(final String landmark) {
        this.landmark = landmark;
    }

    @SuppressWarnings("all")
    public void setHouseholdId(final Long householdId) {
        this.householdId = householdId;
    }

    @SuppressWarnings("all")
    public void setActive(final Integer active) {
        this.active = active;
    }

    @SuppressWarnings("all")
    public void setArchived(final int archived) {
        this.archived = archived;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HouseholdMigrationDTO)) return false;
        final HouseholdMigrationDTO other = (HouseholdMigrationDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArchived() != other.getArchived()) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$householdId = this.getHouseholdId();
        final Object other$householdId = other.getHouseholdId();
        if (this$householdId == null ? other$householdId != null : !this$householdId.equals(other$householdId)) return false;
        final Object this$active = this.getActive();
        final Object other$active = other.getActive();
        if (this$active == null ? other$active != null : !this$active.equals(other$active)) return false;
        final Object this$zipCode = this.getZipCode();
        final Object other$zipCode = other.getZipCode();
        if (this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode)) return false;
        final Object this$city = this.getCity();
        final Object other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) return false;
        final Object this$street = this.getStreet();
        final Object other$street = other.getStreet();
        if (this$street == null ? other$street != null : !this$street.equals(other$street)) return false;
        final Object this$landmark = this.getLandmark();
        final Object other$landmark = other.getLandmark();
        if (this$landmark == null ? other$landmark != null : !this$landmark.equals(other$landmark)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof HouseholdMigrationDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArchived();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $householdId = this.getHouseholdId();
        result = result * PRIME + ($householdId == null ? 43 : $householdId.hashCode());
        final Object $active = this.getActive();
        result = result * PRIME + ($active == null ? 43 : $active.hashCode());
        final Object $zipCode = this.getZipCode();
        result = result * PRIME + ($zipCode == null ? 43 : $zipCode.hashCode());
        final Object $city = this.getCity();
        result = result * PRIME + ($city == null ? 43 : $city.hashCode());
        final Object $street = this.getStreet();
        result = result * PRIME + ($street == null ? 43 : $street.hashCode());
        final Object $landmark = this.getLandmark();
        result = result * PRIME + ($landmark == null ? 43 : $landmark.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "HouseholdMigrationDTO(id=" + this.getId() + ", zipCode=" + this.getZipCode() + ", city=" + this.getCity() + ", street=" + this.getStreet() + ", landmark=" + this.getLandmark() + ", householdId=" + this.getHouseholdId() + ", active=" + this.getActive() + ", archived=" + this.getArchived() + ")";
    }
    //</editor-fold>
}
