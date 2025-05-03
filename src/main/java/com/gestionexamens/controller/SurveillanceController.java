package com.gestionexamens.controller;

import com.gestionexamens.model.Enseignant;
import com.gestionexamens.model.Surveillance;
import com.gestionexamens.service.EnseignantService;
import com.gestionexamens.service.SurveillanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveillances")
public class SurveillanceController {

    private final SurveillanceService surveillanceService;
    private final EnseignantService enseignantService;

    @Autowired
    public SurveillanceController(SurveillanceService surveillanceService, EnseignantService enseignantService) {
        this.surveillanceService = surveillanceService;
        this.enseignantService = enseignantService;
    }

    @GetMapping
    public ResponseEntity<List<Surveillance>> getAllSurveillances() {
        List<Surveillance> surveillances = surveillanceService.findAll();
        return new ResponseEntity<>(surveillances, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Surveillance> getSurveillanceById(@PathVariable Long id) {
        return surveillanceService.findById(id)
                .map(surveillance -> new ResponseEntity<>(surveillance, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/enseignant/{enseignantId}")
    public ResponseEntity<List<Surveillance>> getSurveillancesByEnseignant(@PathVariable Long enseignantId) {
        Optional<Enseignant> enseignant = enseignantService.findById(enseignantId);
        if (enseignant.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Surveillance> surveillances = surveillanceService.findByEnseignant(enseignant.get());
        return new ResponseEntity<>(surveillances, HttpStatus.OK);
    }

    @GetMapping("/enseignant/{enseignantId}/date/{date}")
    public ResponseEntity<List<Surveillance>> getSurveillancesByEnseignantAndDate(
            @PathVariable Long enseignantId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        
        Optional<Enseignant> enseignant = enseignantService.findById(enseignantId);
        if (enseignant.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Surveillance> surveillances = surveillanceService.findByEnseignantAndDate(enseignant.get(), date);
        return new ResponseEntity<>(surveillances, HttpStatus.OK);
    }

    @GetMapping("/examen/{examenId}")
    public ResponseEntity<List<Surveillance>> getSurveillancesByExamen(@PathVariable Long examenId) {
        List<Surveillance> surveillances = surveillanceService.findByExamenId(examenId);
        return new ResponseEntity<>(surveillances, HttpStatus.OK);
    }

    @GetMapping("/salle/{salleId}")
    public ResponseEntity<List<Surveillance>> getSurveillancesBySalle(@PathVariable Long salleId) {
        List<Surveillance> surveillances = surveillanceService.findBySalleId(salleId);
        return new ResponseEntity<>(surveillances, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Surveillance> createSurveillance(@RequestBody Surveillance surveillance) {
        Surveillance savedSurveillance = surveillanceService.save(surveillance);
        return new ResponseEntity<>(savedSurveillance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Surveillance> updateSurveillance(@PathVariable Long id, @RequestBody Surveillance surveillance) {
        return surveillanceService.findById(id)
                .map(existingSurveillance -> {
                    surveillance.setId(id);
                    Surveillance updatedSurveillance = surveillanceService.save(surveillance);
                    return new ResponseEntity<>(updatedSurveillance, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurveillance(@PathVariable Long id) {
        return surveillanceService.findById(id)
                .map(surveillance -> {
                    surveillanceService.deleteById(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
