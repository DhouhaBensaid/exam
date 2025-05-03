package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "niveaux")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Niveau {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String code;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private String anneeAcademique;
    
    @ManyToOne
    @JoinColumn(name = "specialite_id", nullable = false)
    @JsonIgnoreProperties({"niveaux", "matieres", "salles"})
    private Specialite specialite;
    
    @OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("niveau")
    private List<Matiere> matieres = new ArrayList<>();
    
    @OneToMany(mappedBy = "niveau", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("niveau")
    private List<Etudiant> etudiants = new ArrayList<>();
}
