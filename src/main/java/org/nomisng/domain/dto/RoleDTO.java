package org.nomisng.domain.dto;

import org.nomisng.domain.entity.Permission;
import java.util.List;

public class RoleDTO {
    private Long id;
    private String name;
    private String code;
    private List<Permission> permissions;

    //<editor-fold defaultstate="collapsed" desc="delombok">
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
    public List<Permission> getPermissions() {
        return this.permissions;
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
    public void setPermissions(final List<Permission> permissions) {
        this.permissions = permissions;
    }

    @SuppressWarnings("all")
    public RoleDTO() {
    }
    //</editor-fold>
}
