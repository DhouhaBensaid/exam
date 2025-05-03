package com.gestionexamens.controller;

import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import com.gestionexamens.service.NiveauService;
import com.gestionexamens.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/niveaux")
public class NiveauController {
    
    private final NiveauService niveauService;
    private final SpecialiteService specialiteService;
    
    @Autowired
    public NiveauController(NiveauService niveauService, SpecialiteService specialiteService) {
        this.niveauService = niveauService;
        this.specialiteService = specialiteService;
    }
    
    @GetMapping
    public ResponseEntity<List<Niveau>> getAllNiveaux() {
        List<Niveau> niveaux = niveauService.findAll();
        return new ResponseEntity<>(niveaux, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Niveau> getNiveauById(@PathVariable Long id) {
        return niveauService.findById(id)
                .map(niveau -> new ResponseEntity<>(niveau, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/specialite/{specialiteId}")
    public ResponseEntity<List<Niveau>> getNiveauxBySpecialite(@PathVariable Long specialiteId) {
        return specialiteService.findById(specialiteId)
                .map(specialite -> {
                    List<Niveau> niveaux = niveauService.findBySpecialite(specialite);
                    return new ResponseEntity<>(niveaux, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/specialite/{specialiteId}/annee/{anneeAcademique}")
    public ResponseEntity<List<Niveau>> getNiveauxBySpecialiteAndAnnee(
            @PathVariable Long specialiteId,
            @PathVariable String anneeAcademique) {
        return specialiteService.findById(specialiteId)
                .map(specialite -> {
                    List<Niveau> niveaux = niveauService.findBySpecialiteAndAnneeAcademique(specialite, anneeAcademique);
                    return new ResponseEntity<>(niveaux, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Niveau> createNiveau(@RequestBody Niveau niveau) {
        Niveau savedNiveau = niveauService.save(niveau);
        return new ResponseEntity<>(savedNiveau, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Niveau> updateNiveau(@PathVariable Long id, @RequestBody Niveau niveau) {
        return niveauService.findById(id)
                .map(existingNiveau -> {
                    niveau.setId(id);
                    Niveau updatedNiveau = niveauService.save(niveau);
                    return new ResponseEntity<>(updatedNiveau, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNiveau(@PathVariable Long id) {
        return niveauService.findById(id)
                .map(niveau -> {
                    niveauService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
