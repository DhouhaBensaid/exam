package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "examens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Examen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private LocalDate dateExamen;
    
    @Column(nullable = false)
    private LocalTime heureDebut;
    
    @Column(nullable = false)
    private LocalTime heureFin;
    
    private String session;
    
    private String type;
    
    private String anneeAcademique;
    
    @ManyToOne
    @JoinColumn(name = "matiere_id", nullable = false)
    @JsonIgnoreProperties({"examens"})
    private Matiere matiere;
    
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examen")
    private List<Surveillance> surveillances = new ArrayList<>();
    
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examen")
    private List<Affectation> affectations = new ArrayList<>();
    
    @OneToMany(mappedBy = "examen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("examen")
    private List<Presence> presences = new ArrayList<>();
}
