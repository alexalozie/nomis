package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cbo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cbo extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "code")
    private String code;
    @Basic
    @Column(name = "archived")
    private Integer archived;
    @OneToMany(mappedBy = "cboByCboId")
    @JsonIgnore
    private List<CboProject> cboDonorImplementerOrganisationUnitsById;

}
