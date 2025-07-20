package org.nomisng.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "application_codeset_standard_codeset")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationCodesetStandardCodeset extends Audit {
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    @Column(name = "application_codeset_id")
    private Long applicationCodesetId;
    @Basic
    @Column(name = "standard_codeset_id")
    private Long standardCodesetId;
    @ManyToOne
    @JoinColumn(name = "application_codeset_id", referencedColumnName = "id", updatable = false, insertable = false)
    public ApplicationCodeset applicationCodesetByApplicationCodesetId;
    @ManyToOne
    @JoinColumn(name = "standard_codeset_id", referencedColumnName = "id", insertable = false, updatable = false)
    public StandardCodeset standardCodesetByStandardCodesetId;

}
