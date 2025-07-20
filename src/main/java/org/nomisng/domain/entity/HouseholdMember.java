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
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "household_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseholdMember extends JsonBEntity {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "household_id")
    private Long householdId;
    @Type(type = "jsonb")
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "details", nullable = false, columnDefinition = "jsonb")
    private Object details;
    //1 - Caregiver, 2 - OVC, 3 - Other members
    @Basic
    @Column(name = "household_member_type")
    private Integer householdMemberType;
    @Basic
    @Column(name = "cbo_project_id", nullable = false)
    private Long cboProjectId;
    @Basic
    @Column(name = "archived")
    private int archived;
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
    @ManyToOne
    @JoinColumn(name = "household_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    public Household householdByHouseholdId;
    @OneToMany(mappedBy = "householdMemberByHouseholdMemberId")
    @JsonIgnore
    private List<Encounter> encounterByHouseholdMemberId;
    @ManyToOne
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", updatable = false, insertable = false)
    private CboProject householdMemberByCboProjectId;
    @Basic
    @Column(name = "unique_id")
    private String uniqueId;
    @OneToMany(mappedBy = "memberByMemberId")
    private List<MemberFlag> memberFlagsById;
    @Transient
    private List<Flag> flags;

}
