package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "domain")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domain extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "code", updatable = false)
    private String code;
    @Basic
    @Column(name = "archived")
    private int archived;
    @OneToMany(mappedBy = "domainByDomainId")
    @JsonIgnore
    private List<OvcService> servicesById;

    @PrePersist
    public void update() {
        if (this.code == null || this.code.isEmpty()) {
            this.code = UUID.randomUUID().toString();
        }
    }
}
