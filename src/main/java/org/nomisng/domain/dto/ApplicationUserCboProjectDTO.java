package org.nomisng.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ApplicationUserCboProjectDTO {
    private Long id;
    @NotNull(message = "applicationUserId is mandatory")
    private Long applicationUserId;
    @NotEmpty(message = "cboProjectIds should not be empty")
    @NotNull(message = "cboProjectIds is mandatory")
    private List<Long> cboProjectIds;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public ApplicationUserCboProjectDTO() {
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public Long getApplicationUserId() {
        return this.applicationUserId;
    }

    @SuppressWarnings("all")
    public List<Long> getCboProjectIds() {
        return this.cboProjectIds;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setApplicationUserId(final Long applicationUserId) {
        this.applicationUserId = applicationUserId;
    }

    @SuppressWarnings("all")
    public void setCboProjectIds(final List<Long> cboProjectIds) {
        this.cboProjectIds = cboProjectIds;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ApplicationUserCboProjectDTO)) return false;
        final ApplicationUserCboProjectDTO other = (ApplicationUserCboProjectDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$applicationUserId = this.getApplicationUserId();
        final Object other$applicationUserId = other.getApplicationUserId();
        if (this$applicationUserId == null ? other$applicationUserId != null : !this$applicationUserId.equals(other$applicationUserId)) return false;
        final Object this$cboProjectIds = this.getCboProjectIds();
        final Object other$cboProjectIds = other.getCboProjectIds();
        if (this$cboProjectIds == null ? other$cboProjectIds != null : !this$cboProjectIds.equals(other$cboProjectIds)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ApplicationUserCboProjectDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $applicationUserId = this.getApplicationUserId();
        result = result * PRIME + ($applicationUserId == null ? 43 : $applicationUserId.hashCode());
        final Object $cboProjectIds = this.getCboProjectIds();
        result = result * PRIME + ($cboProjectIds == null ? 43 : $cboProjectIds.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ApplicationUserCboProjectDTO(id=" + this.getId() + ", applicationUserId=" + this.getApplicationUserId() + ", cboProjectIds=" + this.getCboProjectIds() + ")";
    }
    //</editor-fold>
}
