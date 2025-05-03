package com.gestionexamens.controller;

import com.gestionexamens.model.Salle;
import com.gestionexamens.service.SalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/salles")
public class SalleController {
    
    private final SalleService salleService;
    
    @Autowired
    public SalleController(SalleService salleService) {
        this.salleService = salleService;
    }
    
    @GetMapping
    public ResponseEntity<List<Salle>> getAllSalles() {
        List<Salle> salles = salleService.findAll();
        return new ResponseEntity<>(salles, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Salle> getSalleById(@PathVariable Long id) {
        return salleService.findById(id)
                .map(salle -> new ResponseEntity<>(salle, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Salle> getSalleByCode(@PathVariable String code) {
        return salleService.findByCode(code)
                .map(salle -> new ResponseEntity<>(salle, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/capacite/{capacite}")
    public ResponseEntity<List<Salle>> getSallesByCapaciteMinimum(@PathVariable Integer capacite) {
        List<Salle> salles = salleService.findByCapaciteGreaterThanEqual(capacite);
        return new ResponseEntity<>(salles, HttpStatus.OK);
    }
    
    @GetMapping("/disponible/{disponible}")
    public ResponseEntity<List<Salle>> getSallesByDisponibilite(@PathVariable Boolean disponible) {
        List<Salle> salles = salleService.findByDisponible(disponible);
        return new ResponseEntity<>(salles, HttpStatus.OK);
    }
    
    @GetMapping("/specialite/{specialiteId}")
    public ResponseEntity<List<Salle>> getSallesBySpecialite(@PathVariable Long specialiteId) {
        List<Salle> salles = salleService.findSallesBySpecialite(specialiteId);
        return new ResponseEntity<>(salles, HttpStatus.OK);
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<Salle>> getSallesDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureDebut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime heureFin) {
        List<Salle> salles = salleService.findAvailableSalles(date, heureDebut, heureFin);
        return new ResponseEntity<>(salles, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Salle> createSalle(@RequestBody Salle salle) {
        Salle savedSalle = salleService.save(salle);
        return new ResponseEntity<>(savedSalle, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Salle> updateSalle(@PathVariable Long id, @RequestBody Salle salle) {
        return salleService.findById(id)
                .map(existingSalle -> {
                    salle.setId(id);
                    Salle updatedSalle = salleService.save(salle);
                    return new ResponseEntity<>(updatedSalle, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        return salleService.findById(id)
                .map(salle -> {
                    salleService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}/disponibilite")
    public ResponseEntity<Salle> updateDisponibilite(@PathVariable Long id, @RequestParam Boolean disponible) {
        return salleService.findById(id)
                .map(salle -> {
                    salle.setDisponible(disponible);
                    Salle updatedSalle = salleService.save(salle);
                    return new ResponseEntity<>(updatedSalle, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
