package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "encounter")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encounter extends Audit implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "date_encounter")
    private LocalDateTime dateEncounter;
    @Basic
    @Column(name = "form_code")
    private String formCode;
    @Basic
    @Column(name = "archived")
    private Integer archived;
    @Basic
    @Column(name = "household_id")
    private Long householdId;
    /*@Basic
    @Column(name = "service_code")
    private String ovcServiceCode;*/
    @Basic
    @Column(name = "household_member_id")
    private Long householdMemberId;
    @Basic
    @Column(name = "cbo_project_id")
    @JsonIgnore
    private Long cboProjectId;
    @OneToMany(mappedBy = "encounterByEncounterId")
    @ToStringExclude
    private List<FormData> formData;
    @ManyToOne
    @JoinColumn(name = "household_member_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    @ToStringExclude
    private HouseholdMember householdMemberByHouseholdMemberId;
    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "form_code", referencedColumnName = "code", updatable = false, insertable = false)
    private Form formByFormCode;
    @Transient
    @JsonIgnore
    private String formName;
    @Transient
    @JsonIgnore
    private String firstName;
    @Transient
    @JsonIgnore
    private String lastName;
    @Transient
    @JsonIgnore
    private String otherNames;
    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private Household householdByHouseholdId;
    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    private CboProject encounterByCboProjectId;

}
