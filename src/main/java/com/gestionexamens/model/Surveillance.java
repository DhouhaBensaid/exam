package com.gestionexamens.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "surveillances", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"enseignant_id", "examen_id", "salle_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Surveillance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    @JsonIgnoreProperties({"surveillances"})
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "examen_id", nullable = false)
    @JsonIgnoreProperties({"surveillances", "affectations", "presences"})
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "salle_id", nullable = false)
    @JsonIgnoreProperties({"surveillances", "affectations", "presences"})
    private Salle salle;

    private Boolean confirmee = false;
    @Column(name = "date")
    private LocalDate date;


}
