package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "household_migration")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdMigration extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "zip_code")
    private String zipCode;
    @Basic
    @Column(name = "city")
    private String city;
    @Basic
    @Column(name = "street")
    private String street;
    @Basic
    @Column(name = "landmark")
    private String landmark;
    @Basic
    @Column(name = "household_id")
    private Long householdId;
    @Basic
    @Column(name = "migration_type")
    private Long migrationType;
    @Basic
    @Column(name = "active", nullable = false)
    private Integer active;
    @Basic
    @Column(name = "organisation_unit_id", nullable = false)
    private Long organisationUnitId;
    /*@Basic
    @Column(name = "country_id")
    private Long countryId;
    @Basic
    @Column(name = "state_id")
    private Long stateId;
    @Basic
    @Column(name = "province_id")
    private Long provinceId;
    @Basic
    @Column(name = "ward_id")
    private Long wardId;*/
    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;

    @PrePersist
    public void persist() {
        if (this.householdByHouseholdId != null && this.householdByHouseholdId.getId() != null) {
            this.householdId = householdByHouseholdId.getId();
        }
    }

    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    public CboProject cboProjectByCboProjectId;
    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    public Household householdByHouseholdId;

}
