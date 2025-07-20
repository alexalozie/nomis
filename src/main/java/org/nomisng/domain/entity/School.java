package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringExclude;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "school")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class School extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotNull(message = "State is required")
    private Long stateId;
    @Column(nullable = false)
    @NotNull(message = "LGA is required")
    private Long lgaId;
    @Column(nullable = false)
    @NotNull(message = "Name is required")
    private String name;
    @Basic
    @Column(name = "type")
    private String type;
    private String address;
    private Integer archived = 0;

}
