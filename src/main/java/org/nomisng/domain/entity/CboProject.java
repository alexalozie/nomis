package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cbo_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CboProject {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "cbo_id")
    private Long cboId;
    @Basic
    @Column(name = "donor_id")
    private Long donorId;
    @Basic
    @Column(name = "implementer_id")
    private Long implementerId;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "archived")
    private Integer archived;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "cbo_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Cbo cboByCboId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "donor_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Donor donorByDonorId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "implementer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Implementer implementerByImplementerId;
    @Transient
    @JsonIgnore
    private String cboName;
    @Transient
    @JsonIgnore
    private String donorName;
    @Transient
    @JsonIgnore
    private String implementerName;
    @Transient
    @JsonIgnore
    private String organisationUnitName;
    @Transient
    @JsonIgnore
    private List organisationUnits;
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    private List<CboProjectLocation> cboProjectLocationsById;
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    private List<HouseholdMigration> householdMigrationsById;
    @OneToMany(mappedBy = "householdMemberByCboProjectId")
    @JsonIgnore
    private List<HouseholdMember> householdMembersById;
    @OneToMany(mappedBy = "encounterByCboProjectId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Encounter> encountersById;
    @OneToMany(mappedBy = "cboProjectByCboProjectId", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<FormData> formData;
/*    @OneToMany(mappedBy = "cboProjectByCurrentCboProjectId")
    @JsonIgnore
    @ToString.Exclude
    public List<User> users;*/
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    private List<ApplicationUserCboProject> applicationUserCboProjectsById;
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    private List<ApplicationUserCboProject> applicationUserCboProjectById;
    @OneToMany(mappedBy = "cboProjectByCboProjectId")
    @JsonIgnore
    private List<Household> households;

}
