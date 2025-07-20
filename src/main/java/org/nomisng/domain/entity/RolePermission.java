package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "role_permission")
@IdClass(RolePermissionPK.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @Column(name = "role_id", nullable = false)
    private Long roleId;
    @Id
    @Column(name = "permission_id", nullable = false)
    private Long permissionId;
    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role roleByRoleId;
    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Permission permissionByPermissionId;

}
