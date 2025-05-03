package com.gestionexamens.controller;

import com.gestionexamens.model.Enseignant;
import com.gestionexamens.service.EnseignantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {
    
    private final EnseignantService enseignantService;
    
    @Autowired
    public EnseignantController(EnseignantService enseignantService) {
        this.enseignantService = enseignantService;
    }
    
    @GetMapping
    public ResponseEntity<List<Enseignant>> getAllEnseignants() {
        List<Enseignant> enseignants = enseignantService.findAll();
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Enseignant> getEnseignantById(@PathVariable Long id) {
        return enseignantService.findById(id)
                .map(enseignant -> new ResponseEntity<>(enseignant, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<Enseignant> getEnseignantByMatricule(@PathVariable String matricule) {
        return enseignantService.findByMatricule(matricule)
                .map(enseignant -> new ResponseEntity<>(enseignant, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/specialite/{specialite}")
    public ResponseEntity<List<Enseignant>> getEnseignantsBySpecialite(@PathVariable String specialite) {
        List<Enseignant> enseignants = enseignantService.findBySpecialite(specialite);
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }
    
    @GetMapping("/examen/{examenId}")
    public ResponseEntity<List<Enseignant>> getEnseignantsByExamen(@PathVariable Long examenId) {
        List<Enseignant> enseignants = enseignantService.findByExamenId(examenId);
        return new ResponseEntity<>(enseignants, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Enseignant> createEnseignant(@RequestBody Enseignant enseignant) {
        Enseignant savedEnseignant = enseignantService.save(enseignant);
        return new ResponseEntity<>(savedEnseignant, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Enseignant> updateEnseignant(@PathVariable Long id, @RequestBody Enseignant enseignant) {
        return enseignantService.findById(id)
                .map(existingEnseignant -> {
                    enseignant.setId(id);
                    Enseignant updatedEnseignant = enseignantService.save(enseignant);
                    return new ResponseEntity<>(updatedEnseignant, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnseignant(@PathVariable Long id) {
        return enseignantService.findById(id)
                .map(enseignant -> {
                    enseignantService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping("/{enseignantId}/examen/{examenId}/salle/{salleId}")
    public ResponseEntity<?> assignerSurveillance(
            @PathVariable Long enseignantId,
            @PathVariable Long examenId,
            @PathVariable Long salleId) {
        
        try {
            enseignantService.assignerSurveillance(enseignantId, examenId, salleId);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/surveillance/{surveillanceId}")
    public ResponseEntity<Void> supprimerSurveillance(@PathVariable Long surveillanceId) {
        try {
            enseignantService.supprimerSurveillance(surveillanceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
