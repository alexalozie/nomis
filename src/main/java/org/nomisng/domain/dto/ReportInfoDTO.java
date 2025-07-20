package org.nomisng.domain.dto;

import javax.validation.constraints.NotBlank;

public class ReportInfoDTO {
    private Long id;
    @NotBlank(message = "name is mandatory")
    private String name;
    private String description;
    private String reportFormat;
    @NotBlank(message = "template is mandatory")
    private String template;
    private Object resourceObject;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public ReportInfoDTO() {
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
    public String getReportFormat() {
        return this.reportFormat;
    }

    @SuppressWarnings("all")
    public String getTemplate() {
        return this.template;
    }

    @SuppressWarnings("all")
    public Object getResourceObject() {
        return this.resourceObject;
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
    public void setReportFormat(final String reportFormat) {
        this.reportFormat = reportFormat;
    }

    @SuppressWarnings("all")
    public void setTemplate(final String template) {
        this.template = template;
    }

    @SuppressWarnings("all")
    public void setResourceObject(final Object resourceObject) {
        this.resourceObject = resourceObject;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReportInfoDTO)) return false;
        final ReportInfoDTO other = (ReportInfoDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description)) return false;
        final Object this$reportFormat = this.getReportFormat();
        final Object other$reportFormat = other.getReportFormat();
        if (this$reportFormat == null ? other$reportFormat != null : !this$reportFormat.equals(other$reportFormat)) return false;
        final Object this$template = this.getTemplate();
        final Object other$template = other.getTemplate();
        if (this$template == null ? other$template != null : !this$template.equals(other$template)) return false;
        final Object this$resourceObject = this.getResourceObject();
        final Object other$resourceObject = other.getResourceObject();
        if (this$resourceObject == null ? other$resourceObject != null : !this$resourceObject.equals(other$resourceObject)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ReportInfoDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $reportFormat = this.getReportFormat();
        result = result * PRIME + ($reportFormat == null ? 43 : $reportFormat.hashCode());
        final Object $template = this.getTemplate();
        result = result * PRIME + ($template == null ? 43 : $template.hashCode());
        final Object $resourceObject = this.getResourceObject();
        result = result * PRIME + ($resourceObject == null ? 43 : $resourceObject.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ReportInfoDTO(id=" + this.getId() + ", name=" + this.getName() + ", description=" + this.getDescription() + ", reportFormat=" + this.getReportFormat() + ", template=" + this.getTemplate() + ", resourceObject=" + this.getResourceObject() + ")";
    }
    //</editor-fold>
}
