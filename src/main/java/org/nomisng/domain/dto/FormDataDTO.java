package org.nomisng.domain.dto;

import javax.validation.constraints.NotNull;

public class FormDataDTO {
    private Long id;
    @NotNull(message = "encounterId is mandatory")
    private Long encounterId;
    @NotNull(message = "data is mandatory")
    private Object data;
    private int archived;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public FormDataDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public Long getEncounterId() {
        return this.encounterId;
    }

    @SuppressWarnings("all")
    public Object getData() {
        return this.data;
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
    public void setEncounterId(final Long encounterId) {
        this.encounterId = encounterId;
    }

    @SuppressWarnings("all")
    public void setData(final Object data) {
        this.data = data;
    }

    @SuppressWarnings("all")
    public void setArchived(final int archived) {
        this.archived = archived;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof FormDataDTO)) return false;
        final FormDataDTO other = (FormDataDTO) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getArchived() != other.getArchived()) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$encounterId = this.getEncounterId();
        final Object other$encounterId = other.getEncounterId();
        if (this$encounterId == null ? other$encounterId != null : !this$encounterId.equals(other$encounterId)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof FormDataDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getArchived();
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $encounterId = this.getEncounterId();
        result = result * PRIME + ($encounterId == null ? 43 : $encounterId.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "FormDataDTO(id=" + this.getId() + ", encounterId=" + this.getEncounterId() + ", data=" + this.getData() + ", archived=" + this.getArchived() + ")";
    }
    //</editor-fold>
}
