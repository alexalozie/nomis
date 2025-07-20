package org.nomisng.domain.dto;

import java.time.LocalDate;

public class QuarterDto {
    LocalDate previousQuarterStartDate;
    LocalDate previousQuarterEndDate;
    LocalDate currentQuarterStartDate;
    LocalDate currentQuarterEndDate;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public QuarterDto() {
    }

    @SuppressWarnings("all")
    public LocalDate getPreviousQuarterStartDate() {
        return this.previousQuarterStartDate;
    }

    @SuppressWarnings("all")
    public LocalDate getPreviousQuarterEndDate() {
        return this.previousQuarterEndDate;
    }

    @SuppressWarnings("all")
    public LocalDate getCurrentQuarterStartDate() {
        return this.currentQuarterStartDate;
    }

    @SuppressWarnings("all")
    public LocalDate getCurrentQuarterEndDate() {
        return this.currentQuarterEndDate;
    }

    @SuppressWarnings("all")
    public void setPreviousQuarterStartDate(final LocalDate previousQuarterStartDate) {
        this.previousQuarterStartDate = previousQuarterStartDate;
    }

    @SuppressWarnings("all")
    public void setPreviousQuarterEndDate(final LocalDate previousQuarterEndDate) {
        this.previousQuarterEndDate = previousQuarterEndDate;
    }

    @SuppressWarnings("all")
    public void setCurrentQuarterStartDate(final LocalDate currentQuarterStartDate) {
        this.currentQuarterStartDate = currentQuarterStartDate;
    }

    @SuppressWarnings("all")
    public void setCurrentQuarterEndDate(final LocalDate currentQuarterEndDate) {
        this.currentQuarterEndDate = currentQuarterEndDate;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof QuarterDto)) return false;
        final QuarterDto other = (QuarterDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$previousQuarterStartDate = this.getPreviousQuarterStartDate();
        final Object other$previousQuarterStartDate = other.getPreviousQuarterStartDate();
        if (this$previousQuarterStartDate == null ? other$previousQuarterStartDate != null : !this$previousQuarterStartDate.equals(other$previousQuarterStartDate)) return false;
        final Object this$previousQuarterEndDate = this.getPreviousQuarterEndDate();
        final Object other$previousQuarterEndDate = other.getPreviousQuarterEndDate();
        if (this$previousQuarterEndDate == null ? other$previousQuarterEndDate != null : !this$previousQuarterEndDate.equals(other$previousQuarterEndDate)) return false;
        final Object this$currentQuarterStartDate = this.getCurrentQuarterStartDate();
        final Object other$currentQuarterStartDate = other.getCurrentQuarterStartDate();
        if (this$currentQuarterStartDate == null ? other$currentQuarterStartDate != null : !this$currentQuarterStartDate.equals(other$currentQuarterStartDate)) return false;
        final Object this$currentQuarterEndDate = this.getCurrentQuarterEndDate();
        final Object other$currentQuarterEndDate = other.getCurrentQuarterEndDate();
        if (this$currentQuarterEndDate == null ? other$currentQuarterEndDate != null : !this$currentQuarterEndDate.equals(other$currentQuarterEndDate)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof QuarterDto;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $previousQuarterStartDate = this.getPreviousQuarterStartDate();
        result = result * PRIME + ($previousQuarterStartDate == null ? 43 : $previousQuarterStartDate.hashCode());
        final Object $previousQuarterEndDate = this.getPreviousQuarterEndDate();
        result = result * PRIME + ($previousQuarterEndDate == null ? 43 : $previousQuarterEndDate.hashCode());
        final Object $currentQuarterStartDate = this.getCurrentQuarterStartDate();
        result = result * PRIME + ($currentQuarterStartDate == null ? 43 : $currentQuarterStartDate.hashCode());
        final Object $currentQuarterEndDate = this.getCurrentQuarterEndDate();
        result = result * PRIME + ($currentQuarterEndDate == null ? 43 : $currentQuarterEndDate.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "QuarterDto(previousQuarterStartDate=" + this.getPreviousQuarterStartDate() + ", previousQuarterEndDate=" + this.getPreviousQuarterEndDate() + ", currentQuarterStartDate=" + this.getCurrentQuarterStartDate() + ", currentQuarterEndDate=" + this.getCurrentQuarterEndDate() + ")";
    }
}
    //</editor-fold>
