package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserRolePK implements Serializable {
    @Column(name = "user_id")
    @Id
    private Long userId;
    @Column(name = "role_id")
    @Id
    private Long roleId;

}
