package org.nomisng.domain.dto;

import javax.validation.constraints.NotBlank;

public class DomainDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    private String code;
    private int archived;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public DomainDTO() {
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
    public String getCode() {
        return this.code;
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
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setCode(final String code) {
        this.code = code;
    }

    @SuppressWarnings("all")
    public void setArchived(final int archived) {
        this.archived = archived;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DomainDTO)) return false;
        final DomainDTO other = (DomainDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArchived() != other.getArchived()) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof DomainDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArchived();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "DomainDTO(id=" + this.getId() + ", name=" + this.getName() + ", code=" + this.getCode() + ", archived=" + this.getArchived() + ")";
    }
    //</editor-fold>
}
