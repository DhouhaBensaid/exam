package com.gestionexamens.controller;

import com.gestionexamens.model.Matiere;
import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import com.gestionexamens.service.MatiereService;
import com.gestionexamens.service.NiveauService;
import com.gestionexamens.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matieres")
public class MatiereController {
    
    private final MatiereService matiereService;
    private final SpecialiteService specialiteService;
    private final NiveauService niveauService;
    
    @Autowired
    public MatiereController(MatiereService matiereService, SpecialiteService specialiteService, NiveauService niveauService) {
        this.matiereService = matiereService;
        this.specialiteService = specialiteService;
        this.niveauService = niveauService;
    }
    
    @GetMapping
    public ResponseEntity<List<Matiere>> getAllMatieres() {
        List<Matiere> matieres = matiereService.findAll();
        return new ResponseEntity<>(matieres, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Matiere> getMatiereById(@PathVariable Long id) {
        return matiereService.findById(id)
                .map(matiere -> new ResponseEntity<>(matiere, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Matiere> getMatiereByCode(@PathVariable String code) {
        return matiereService.findByCode(code)
                .map(matiere -> new ResponseEntity<>(matiere, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/specialite/{specialiteId}")
    public ResponseEntity<List<Matiere>> getMatieresBySpecialite(@PathVariable Long specialiteId) {
        return specialiteService.findById(specialiteId)
                .map(specialite -> {
                    List<Matiere> matieres = matiereService.findBySpecialite(specialite);
                    return new ResponseEntity<>(matieres, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/niveau/{niveauId}")
    public ResponseEntity<List<Matiere>> getMatieresByNiveau(@PathVariable Long niveauId) {
        return niveauService.findById(niveauId)
                .map(niveau -> {
                    List<Matiere> matieres = matiereService.findByNiveau(niveau);
                    return new ResponseEntity<>(matieres, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Matiere> createMatiere(@RequestBody Matiere matiere) {
        Matiere savedMatiere = matiereService.save(matiere);
        return new ResponseEntity<>(savedMatiere, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Matiere> updateMatiere(@PathVariable Long id, @RequestBody Matiere matiere) {
        return matiereService.findById(id)
                .map(existingMatiere -> {
                    matiere.setId(id);
                    Matiere updatedMatiere = matiereService.save(matiere);
                    return new ResponseEntity<>(updatedMatiere, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        return matiereService.findById(id)
                .map(matiere -> {
                    matiereService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
