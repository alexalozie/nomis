package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VocationalCentre extends Audit implements Serializable {
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
    private Boolean archived = false;

}
