package com.gestionexamens.controller;

import com.gestionexamens.dto.PresenceDto;
import com.gestionexamens.service.PresenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    private final PresenceService presenceService;

    @Autowired
    public PresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
    }

    @GetMapping("/etudiant/{etudiantId}")
    public ResponseEntity<List<Map<String, Object>>> getPresencesByEtudiant(@PathVariable Long etudiantId) {
        List<Map<String, Object>> presences = presenceService.findPresencesByEtudiant(etudiantId);
        return new ResponseEntity<>(presences, HttpStatus.OK);
    }

    @GetMapping("/examen/{examenId}/salle/{salleId}")
    public ResponseEntity<List<Map<String, Object>>> getPresencesByExamenAndSalle(
            @PathVariable Long examenId,
            @PathVariable Long salleId) {
        List<Map<String, Object>> presences = presenceService.findPresencesByExamenAndSalle(examenId, salleId);
        return new ResponseEntity<>(presences, HttpStatus.OK);
    }

    @GetMapping("/salle/{salleId}")
    public ResponseEntity<List<Map<String, Object>>> getAbsencesBySalle(@PathVariable Long salleId) {
        List<Map<String, Object>> absences = presenceService.findAbsencesBySalle(salleId);
        return new ResponseEntity<>(absences, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePresence(@RequestBody PresenceDto presenceDto) {
        try {
            presenceService.savePresence(
                    presenceDto.getEtudiantId(),
                    presenceDto.getExamenId(),
                    presenceDto.getSalleId(),
                    presenceDto.isPresent()
            );
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save-batch")
    public ResponseEntity<?> savePresenceBatch(@RequestBody List<PresenceDto> presenceDtos) {
        try {
            presenceService.savePresenceBatch(presenceDtos);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
