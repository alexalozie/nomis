package org.nomisng.domain.dto;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class ReportDetailDTO {
    @NotNull(message = "reportId is mandatory")
    private Long reportId;
    private String reportName;
    @NotNull(message = "reportFormat is mandatory")
    private String reportFormat;
    Map<String, Object> parameters;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public ReportDetailDTO() {
    }

    @SuppressWarnings("all")
    public Long getReportId() {
        return this.reportId;
    }

    @SuppressWarnings("all")
    public String getReportName() {
        return this.reportName;
    }

    @SuppressWarnings("all")
    public String getReportFormat() {
        return this.reportFormat;
    }

    @SuppressWarnings("all")
    public Map<String, Object> getParameters() {
        return this.parameters;
    }

    @SuppressWarnings("all")
    public void setReportId(final Long reportId) {
        this.reportId = reportId;
    }

    @SuppressWarnings("all")
    public void setReportName(final String reportName) {
        this.reportName = reportName;
    }

    @SuppressWarnings("all")
    public void setReportFormat(final String reportFormat) {
        this.reportFormat = reportFormat;
    }

    @SuppressWarnings("all")
    public void setParameters(final Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ReportDetailDTO)) return false;
        final ReportDetailDTO other = (ReportDetailDTO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$reportId = this.getReportId();
        final Object other$reportId = other.getReportId();
        if (this$reportId == null ? other$reportId != null : !this$reportId.equals(other$reportId)) return false;
        final Object this$reportName = this.getReportName();
        final Object other$reportName = other.getReportName();
        if (this$reportName == null ? other$reportName != null : !this$reportName.equals(other$reportName)) return false;
        final Object this$reportFormat = this.getReportFormat();
        final Object other$reportFormat = other.getReportFormat();
        if (this$reportFormat == null ? other$reportFormat != null : !this$reportFormat.equals(other$reportFormat)) return false;
        final Object this$parameters = this.getParameters();
        final Object other$parameters = other.getParameters();
        if (this$parameters == null ? other$parameters != null : !this$parameters.equals(other$parameters)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof ReportDetailDTO;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $reportId = this.getReportId();
        result = result * PRIME + ($reportId == null ? 43 : $reportId.hashCode());
        final Object $reportName = this.getReportName();
        result = result * PRIME + ($reportName == null ? 43 : $reportName.hashCode());
        final Object $reportFormat = this.getReportFormat();
        result = result * PRIME + ($reportFormat == null ? 43 : $reportFormat.hashCode());
        final Object $parameters = this.getParameters();
        result = result * PRIME + ($parameters == null ? 43 : $parameters.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "ReportDetailDTO(reportId=" + this.getReportId() + ", reportName=" + this.getReportName() + ", reportFormat=" + this.getReportFormat() + ", parameters=" + this.getParameters() + ")";
    }
    //</editor-fold>
}
