package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "ovc_service")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvcService extends Audit implements Serializable {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "code", updatable = false, unique = true)
    private String code;
    @Basic
    @Column(name = "domain_id")
    private Long domainId;
    @Basic
    @Column(name = "archived")
    private Integer archived;
    /*@JsonIgnore
    @OneToMany(mappedBy = "ovcServiceByOvcServiceCode")
    private List<Form> formByOvcServiceCode;*/
    /*@JsonIgnore
    @OneToMany(mappedBy = "ovcServiceByOvcServiceCode")
    private List<Encounter> encounterByOvcServiceCode;*/
    //1 - caregiver, 2 - ovc, 3 both
    @Basic
    @Column(name = "service_type")
    @NotNull(message = "serviceType cannot be null")
    private Integer serviceType;
    @ManyToOne
    @JoinColumn(name = "domain_id", referencedColumnName = "id", updatable = false, insertable = false)
    @JsonIgnore
    private Domain domainByDomainId;

    @PrePersist
    public void update() {
        if (this.code == null || this.code.isEmpty()) {
            this.code = UUID.randomUUID().toString();
        }
        if (this.archived == null) {
            this.archived = 0;
        }
    }

    @Transient
    private String domainName;

}
