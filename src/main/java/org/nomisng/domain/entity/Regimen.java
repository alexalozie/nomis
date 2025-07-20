package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "REGIMEN")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Regimen implements Serializable, Comparable<Regimen>, Persistable<Long> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "COMPOSITION")
    private String composition;
    private Boolean active = true;
    private Integer priority = 1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regimen")
    @JsonIgnore
    private List<RegimenDrug> regimenDrugs;
    @JoinColumn(name = "REGIMEN_TYPE_ID")
    @ManyToOne(optional = false)
    private RegimenType regimenType;

    @Override
    public int compareTo(Regimen o) {
        return description.compareTo(o.description);
    }

    @Override
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Regimen)) return false;
        Regimen regimen = (Regimen) o;
        return Objects.equals(getId(), regimen.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
