package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String code;
    
    @Column(nullable = false)
    private String nom;
    
    @Column(nullable = false)
    private Integer capacite;
    
    private Boolean disponible = true;
    
    private String localisation;
    
    @ManyToOne
    @JoinColumn(name = "specialite_id")
    @JsonIgnoreProperties({"salles", "niveaux", "matieres"})
    private Specialite specialite;
    
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("salle")
    private List<Surveillance> surveillances = new ArrayList<>();
    
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("salle")
    private List<Affectation> affectations = new ArrayList<>();
    
    @OneToMany(mappedBy = "salle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("salle")
    private List<Presence> presences = new ArrayList<>();
}
