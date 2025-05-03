package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "presences", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"etudiant_id", "examen_id", "salle_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Presence {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    @JsonIgnoreProperties({"presences", "affectations"})
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "examen_id", nullable = false)
    @JsonIgnoreProperties({"presences", "affectations", "surveillances"})
    private Examen examen;
    
    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    @JsonIgnoreProperties({"presences", "affectations", "surveillances"})
    private Salle salle;
    
    private boolean present;
}
