package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "affectations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"etudiant_id", "examen_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Affectation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonIgnoreProperties({"affectations", "presences"})
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "examen_id", nullable = false)
    @JsonIgnoreProperties({"affectations", "surveillances", "presences"})
    private Examen examen;
    
    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    @JsonIgnoreProperties({"affectations", "surveillances", "presences"})
    private Salle salle;
    
    private Integer place;
}
