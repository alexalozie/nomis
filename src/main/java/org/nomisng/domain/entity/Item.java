package org.nomisng.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ITEM")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 45)
    @Column(name = "UNIT_MEASURE")
    private String unitMeasure;
    @Column(name = "MAX_LEVEL")
    private Integer maxLevel;
    @Column(name = "MIN_LEVEL")
    private Integer minLevel;
    @Column(name = "DATE_LAST_RECEIVED")
    private LocalDate dateLastReceived;
    @Column(name = "DATE_LAST_ISSUED")
    private LocalDate dateLastIssued;
    @Column(name = "DATE_LAST_AUDITED")
    private LocalDate dateLastAudited;
    @Size(max = 1)
    @Column(name = "BALANCE_TYPE")
    private String balanceType;
    @Column(name = "UNIT_COST")
    private Double unitCost;
    @Column(name = "BALANCE")
    private Integer balance;
    @Column(name = "TIME_STAMP")
    @JsonIgnore
    private LocalDateTime timeStamp;

}
