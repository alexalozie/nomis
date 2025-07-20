package org.nomisng.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OvcServiceDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    private String code;
    @NotNull(message = "domainId is mandatory")
    private Long domainId;
    private Integer archived;
    @NotNull(message = "serviceType is mandatory")
    private Integer serviceType;
    private String domainName;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public OvcServiceDTO() {
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
    public Long getDomainId() {
        return this.domainId;
    }

    @SuppressWarnings("all")
    public Integer getArchived() {
        return this.archived;
    }

    @SuppressWarnings("all")
    public Integer getServiceType() {
        return this.serviceType;
    }

    @SuppressWarnings("all")
    public String getDomainName() {
        return this.domainName;
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
    public void setDomainId(final Long domainId) {
        this.domainId = domainId;
    }

    @SuppressWarnings("all")
    public void setArchived(final Integer archived) {
        this.archived = archived;
    }

    @SuppressWarnings("all")
    public void setServiceType(final Integer serviceType) {
        this.serviceType = serviceType;
    }

    @SuppressWarnings("all")
    public void setDomainName(final String domainName) {
        this.domainName = domainName;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OvcServiceDTO)) return false;
        final OvcServiceDTO other = (OvcServiceDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$domainId = this.getDomainId();
        final Object other$domainId = other.getDomainId();
        if (this$domainId == null ? other$domainId != null : !this$domainId.equals(other$domainId)) return false;
        final Object this$archived = this.getArchived();
        final Object other$archived = other.getArchived();
        if (this$archived == null ? other$archived != null : !this$archived.equals(other$archived)) return false;
        final Object this$serviceType = this.getServiceType();
        final Object other$serviceType = other.getServiceType();
        if (this$serviceType == null ? other$serviceType != null : !this$serviceType.equals(other$serviceType)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$domainName = this.getDomainName();
        final Object other$domainName = other.getDomainName();
        if (this$domainName == null ? other$domainName != null : !this$domainName.equals(other$domainName)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof OvcServiceDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $domainId = this.getDomainId();
        result = result * PRIME + ($domainId == null ? 43 : $domainId.hashCode());
        final Object $archived = this.getArchived();
        result = result * PRIME + ($archived == null ? 43 : $archived.hashCode());
        final Object $serviceType = this.getServiceType();
        result = result * PRIME + ($serviceType == null ? 43 : $serviceType.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $domainName = this.getDomainName();
        result = result * PRIME + ($domainName == null ? 43 : $domainName.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "OvcServiceDTO(id=" + this.getId() + ", name=" + this.getName() + ", code=" + this.getCode() + ", domainId=" + this.getDomainId() + ", archived=" + this.getArchived() + ", serviceType=" + this.getServiceType() + ", domainName=" + this.getDomainName() + ")";
    }
    //</editor-fold>
}
