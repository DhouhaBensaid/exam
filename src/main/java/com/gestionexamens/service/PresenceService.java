package com.gestionexamens.service;

import com.gestionexamens.dto.PresenceDto;
import com.gestionexamens.model.Etudiant;
import com.gestionexamens.model.Examen;
import com.gestionexamens.model.Presence;
import com.gestionexamens.model.Salle;
import com.gestionexamens.repository.EtudiantRepository;
import com.gestionexamens.repository.ExamenRepository;
import com.gestionexamens.repository.PresenceRepository;
import com.gestionexamens.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final EtudiantRepository etudiantRepository;
    private final ExamenRepository examenRepository;
    private final SalleRepository salleRepository;

    @Autowired
    public PresenceService(
            PresenceRepository presenceRepository,
            EtudiantRepository etudiantRepository,
            ExamenRepository examenRepository,
            SalleRepository salleRepository) {
        this.presenceRepository = presenceRepository;
        this.etudiantRepository = etudiantRepository;
        this.examenRepository = examenRepository;
        this.salleRepository = salleRepository;
    }

    public List<Map<String, Object>> findPresencesByEtudiant(Long etudiantId) {
        List<Object[]> results = presenceRepository.findPresencesByEtudiantId(etudiantId);
        List<Map<String, Object>> presences = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> presence = new HashMap<>();
            presence.put("examenId", result[0]);
            presence.put("matiereNom", result[1]);
            presence.put("dateExamen", result[2]);
            presence.put("heureDebut", result[3]);
            presence.put("heureFin", result[4]);
            presence.put("salleNom", result[5]);
            presence.put("present", result[6]);
            presences.add(presence);
        }

        return presences;
    }

    public List<Map<String, Object>> findPresencesByExamenAndSalle(Long examenId, Long salleId) {
        List<Object[]> results = presenceRepository.findPresencesByExamenIdAndSalleId(examenId, salleId);
        List<Map<String, Object>> presences = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> presence = new HashMap<>();
            presence.put("etudiantId", result[0]);
            presence.put("matricule", result[1]);
            presence.put("nom", result[2]);
            presence.put("prenom", result[3]);
            presence.put("place", result[4]);
            presence.put("present", result[5]);
            presences.add(presence);
        }

        return presences;
    }

    public List<Map<String, Object>> findAbsencesBySalle(Long salleId) {
        List<Object[]> results = presenceRepository.findAbsencesBySalleId(salleId);
        List<Map<String, Object>> absences = new ArrayList<>();

        for (Object[] result : results) {
            Map<String, Object> absence = new HashMap<>();
            absence.put("etudiantId", result[0]);
            absence.put("matricule", result[1]);
            absence.put("nom", result[2]);
            absence.put("prenom", result[3]);
            absence.put("examenId", result[4]);
            absence.put("matiereNom", result[5]);
            absence.put("dateExamen", result[6]);
            absence.put("heureDebut", result[7]);
            absence.put("heureFin", result[8]);
            absences.add(absence);
        }

        return absences;
    }

    @Transactional
    public void savePresence(Long etudiantId, Long examenId, Long salleId, boolean present) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        Optional<Examen> examenOpt = examenRepository.findById(examenId);
        Optional<Salle> salleOpt = salleRepository.findById(salleId);

        if (etudiantOpt.isEmpty() || examenOpt.isEmpty() || salleOpt.isEmpty()) {
            throw new IllegalArgumentException("Étudiant, examen ou salle non trouvé");
        }

        Presence presence = presenceRepository.findByEtudiantIdAndExamenIdAndSalleId(etudiantId, examenId, salleId)
                .orElse(new Presence());

        presence.setEtudiant(etudiantOpt.get());
        presence.setExamen(examenOpt.get());
        presence.setSalle(salleOpt.get());
        presence.setPresent(present);

        presenceRepository.save(presence);
    }

    @Transactional
    public void savePresenceBatch(List<PresenceDto> presenceDtos) {
        for (PresenceDto dto : presenceDtos) {
            savePresence(dto.getEtudiantId(), dto.getExamenId(), dto.getSalleId(), dto.isPresent());
        }
    }
}
