package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "matieres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String nom;
    
    private String description;
    
    private Integer coefficient;
    
    private Integer credits;
    
    @ManyToOne
    @JoinColumn(name = "specialite_id", nullable = false)
    @JsonIgnoreProperties({"matieres", "niveaux", "salles"})
    private Specialite specialite;
    
    @ManyToOne
    @JoinColumn(name = "niveau_id", nullable = false)
    @JsonIgnoreProperties({"matieres", "etudiants"})
    private Niveau niveau;
    
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("matiere")
    private List<Examen> examens = new ArrayList<>();
}
