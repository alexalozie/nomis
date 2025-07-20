package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "cbo_project_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CboProjectLocation {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;
    @Basic
    @Column(name = "organisation_unit_id")
    private Long organisationUnitId;
    @Basic
    @Column(name = "archived")
    private int archived;
    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private CboProject cboProjectByCboProjectId;
    @ManyToOne
    @JoinColumn(name = "organisation_unit_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private OrganisationUnit organisationUnitByOrganisationUnitId;

}
