package org.nomisng.config;

import java.util.Map;

public class DatabaseProperties {
    private Map<String, DataSource> spring;

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public DatabaseProperties() {
    }

    @SuppressWarnings("all")
    public Map<String, DataSource> getSpring() {
        return this.spring;
    }

    @SuppressWarnings("all")
    public void setSpring(final Map<String, DataSource> spring) {
        this.spring = spring;
    }

    @Override
    @SuppressWarnings("all")
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DatabaseProperties)) return false;
        final DatabaseProperties other = (DatabaseProperties) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$spring = this.getSpring();
        final Object other$spring = other.getSpring();
        if (this$spring == null ? other$spring != null : !this$spring.equals(other$spring)) return false;
        return true;
    }

    @SuppressWarnings("all")
    protected boolean canEqual(final Object other) {
        return other instanceof DatabaseProperties;
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $spring = this.getSpring();
        result = result * PRIME + ($spring == null ? 43 : $spring.hashCode());
        return result;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "DatabaseProperties(spring=" + this.getSpring() + ")";
    }
    //</editor-fold>
}
