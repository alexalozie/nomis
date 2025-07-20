package org.nomisng.domain.dto;

import java.util.List;

public class Graduate {
    List<String> service;
    Integer total;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public Graduate() {
    }

    @SuppressWarnings("all")
    public List<String> getService() {
        return this.service;
    }

    @SuppressWarnings("all")
    public Integer getTotal() {
        return this.total;
    }

    @SuppressWarnings("all")
    public void setService(final List<String> service) {
        this.service = service;
    }

    @SuppressWarnings("all")
    public void setTotal(final Integer total) {
        this.total = total;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Graduate)) return false;
        final Graduate other = (Graduate) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$total = this.getTotal();
        final Object other$total = other.getTotal();
        if (this$total == null ? other$total != null : !this$total.equals(other$total)) return false;
        final Object this$service = this.getService();
        final Object other$service = other.getService();
        if (this$service == null ? other$service != null : !this$service.equals(other$service)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof Graduate;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $total = this.getTotal();
        result = result * PRIME + ($total == null ? 43 : $total.hashCode());
        final Object $service = this.getService();
        result = result * PRIME + ($service == null ? 43 : $service.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "Graduate(service=" + this.getService() + ", total=" + this.getTotal() + ")";
    }
    //</editor-fold>
}
