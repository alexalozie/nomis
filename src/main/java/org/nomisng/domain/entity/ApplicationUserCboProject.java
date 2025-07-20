package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "application_user_cbo_project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationUserCboProject extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "application_user_id")
    private Long applicationUserId;
    @Basic
    @Column(name = "cbo_project_id")
    private Long cboProjectId;
    @Basic
    @Column(name = "archived")
    @JsonIgnore
    private int archived;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "application_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User applicationUserByApplicationUserId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "cbo_project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CboProject cboProjectByCboProjectId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "application_user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User getUserByApplicationUserId;

    public String getCboProjectDescription() {
        if (cboProjectId != null) {
            return cboProjectByCboProjectId.getDescription();
        }
        return null;
    }

}
