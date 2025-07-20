package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringExclude;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "member_flag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFlag implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "household_member_id")
    private Long householdMemberId;
    @Basic
    @Column(name = "household_id")
    private Long householdId;
    @Basic
    @Column(name = "flag_id")
    private Long flagId;
    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "household_member_id", referencedColumnName = "id", insertable = false, updatable = false)
    private HouseholdMember memberByMemberId;
    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "household_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Household householdByHouseholdId;
    @ManyToOne
    @JsonIgnore
    @ToStringExclude
    @JoinColumn(name = "flag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Flag flag;

}
