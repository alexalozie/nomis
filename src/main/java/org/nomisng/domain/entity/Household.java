package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.nomisng.security.SecurityUtils;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "household")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Household extends JsonBEntity implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "unique_id")
    private String uniqueId;
    // 1  - active, 2 - graduated
    @Basic
    @Column(name = "status")
    private int status;
    @Basic
    @Column(name = "cbo_project_id")
    @JsonIgnore
    private Long cboProjectId;
    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;
    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private int archived;
    @Basic
    @Column(name = "ward_id")
    @JsonIgnore
    private Long wardId;
    @Basic
    @Column(name = "serial_number")
    @JsonIgnore
    private Long serialNumber;
    @CreatedBy
    @Column(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private String createdBy = SecurityUtils.getCurrentUserLogin().orElse(null);
    @CreatedDate
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now();
    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonIgnore
    private String modifiedBy = SecurityUtils.getCurrentUserLogin().orElse(null);
    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonIgnore
    private LocalDateTime dateModified = LocalDateTime.now();
    @OneToMany(mappedBy = "householdByHouseholdId")
    @JsonIgnore
    private List<Encounter> encountersById;
    /*@ManyToOne
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", updatable = false, insertable = false)
    @ToString.Exclude
    @JsonIgnore
    private OrganisationUnit organisationUnitById;*/
    @OneToMany(mappedBy = "householdByHouseholdId")
    @JsonIgnore
    private List<HouseholdMigration> householdMigrations;
    @OneToMany(mappedBy = "householdByHouseholdId")
    @JsonIgnore
    private List<HouseholdMember> householdMembers;
    @ManyToOne
    @JoinColumn(name = "ward_id", referencedColumnName = "id", updatable = false, insertable = false)
    private OrganisationUnit organisationUnitByOrganisationUnitId;
    @OneToMany(mappedBy = "householdByHouseholdId")
    private List<HouseholdMigration> householdMigrationsById;
    @Transient
    private Object assessment;
    @Transient
    private OrganisationUnit ward;
    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    private CboProject cboProjectByCboProjectId;
    @OneToMany(mappedBy = "householdByHouseholdId")
    private List<MemberFlag> householdFlagsById;
    @Transient
    private List<Flag> flags;
}
