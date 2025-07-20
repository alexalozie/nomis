package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @NonNull
    private String code;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<Permission> permission;

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else if (name != null) {
            return name.hashCode();
        }
        return 0;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null || !(another instanceof Role)) return false;
        Role anotherRole = (Role) another;
        return anotherRole.id != null && (anotherRole.id == this.id);
    }

    @Override
    public String toString() {
        return "Roles{" + "id=" + id + ", name=\'" + name + '\'' + ", code=\'" + code + '\'' + ", permissions=" + permission + '}';
    }

    @OneToMany(mappedBy = "roleByRoleId")
    @JsonIgnore
    public List<ApplicationUserRole> applicationUserRolesById;

}
