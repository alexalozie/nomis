package org.nomisng.util;

import org.nomisng.web.apierror.AccessDeniedException;
import org.nomisng.domain.entity.Permission;
import org.nomisng.service.UserService;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccessRight {
    private final UserService userService;

    public void grantAccess(String formCode, Class clz, Set<String> permissions) {
        if (permissions.contains("permission_all") || permissions.contains(formCode + "_write") || permissions.contains(formCode + "_read") || permissions.contains(formCode + "_delete")) {
            return;
        }
        throw new AccessDeniedException(clz, "formCode", formCode + "");
    }

    //No need to throw an exception
    public Boolean grantAccessForm(String formCode, Set<String> permissions) {
        if (permissions.contains("permission_all") || permissions.contains(formCode + "_write") || permissions.contains(formCode + "_read") || permissions.contains(formCode + "_delete")) {
            return true;
        }
        return false;
    }

    public void grantAccessByAccessType(String formCode, Class clz, String accessType, Set<String> permissions) {
        accessType = accessType.toLowerCase();
        if (permissions.contains("permission_all")) {
            return;
        } else if (permissions.contains(formCode + "_" + accessType)) {
            return;
        } else if (permissions.contains(formCode + "_" + accessType)) {
            return;
        } else if (permissions.contains(formCode + "_" + accessType)) {
            return;
        }
        throw new AccessDeniedException(clz, "formCode", formCode + ", " + accessType);
    }

    public Set<String> getAllPermissionForCurrentUser() {
        Set<String> permissions = new HashSet<>();
        userService.getUserWithRoles().get().getRole().forEach(roles1 -> {
            permissions.addAll(roles1.getPermission().stream().map(Permission::getName).collect(Collectors.toSet()));
        });
        return permissions;
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public AccessRight(final UserService userService) {
        this.userService = userService;
    }
    //</editor-fold>
}
