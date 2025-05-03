package com.gestionexamens.service;

import com.gestionexamens.model.Enseignant;
import com.gestionexamens.model.Examen;
import com.gestionexamens.model.Salle;
import com.gestionexamens.model.Surveillance;
import com.gestionexamens.repository.EnseignantRepository;
import com.gestionexamens.repository.ExamenRepository;
import com.gestionexamens.repository.SalleRepository;
import com.gestionexamens.repository.SurveillanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnseignantService {
    
    private final EnseignantRepository enseignantRepository;
    private final ExamenRepository examenRepository;
    private final SalleRepository salleRepository;
    private final SurveillanceRepository surveillanceRepository;
    
    @Autowired
    public EnseignantService(
            EnseignantRepository enseignantRepository,
            ExamenRepository examenRepository,
            SalleRepository salleRepository,
            SurveillanceRepository surveillanceRepository) {
        this.enseignantRepository = enseignantRepository;
        this.examenRepository = examenRepository;
        this.salleRepository = salleRepository;
        this.surveillanceRepository = surveillanceRepository;
    }
    
    public List<Enseignant> findAll() {
        return enseignantRepository.findAll();
    }
    
    public Optional<Enseignant> findById(Long id) {
        return enseignantRepository.findById(id);
    }
    
    public Optional<Enseignant> findByMatricule(String matricule) {
        return enseignantRepository.findByMatricule(matricule);
    }
    
    public List<Enseignant> findBySpecialite(String specialite) {
        return enseignantRepository.findBySpecialite(specialite);
    }
    
    public List<Enseignant> findByExamenId(Long examenId) {
        return enseignantRepository.findByExamenId(examenId);
    }
    
    public List<Enseignant> searchEnseignants(String searchTerm) {
        return enseignantRepository.searchEnseignants(searchTerm);
    }
    
    public Enseignant save(Enseignant enseignant) {
        return enseignantRepository.save(enseignant);
    }
    
    public void deleteById(Long id) {
        enseignantRepository.deleteById(id);
    }
    
    @Transactional
    public Surveillance assignerSurveillance(Long enseignantId, Long examenId, Long salleId) {
        Optional<Enseignant> enseignantOpt = enseignantRepository.findById(enseignantId);
        Optional<Examen> examenOpt = examenRepository.findById(examenId);
        Optional<Salle> salleOpt = salleRepository.findById(salleId);
        
        if (enseignantOpt.isEmpty() || examenOpt.isEmpty() || salleOpt.isEmpty()) {
            throw new IllegalArgumentException("Enseignant, examen ou salle non trouvé");
        }
        
        Surveillance surveillance = surveillanceRepository
                .findByEnseignantIdAndExamenIdAndSalleId(enseignantId, examenId, salleId)
                .orElse(new Surveillance());
        
        surveillance.setEnseignant(enseignantOpt.get());
        surveillance.setExamen(examenOpt.get());
        surveillance.setSalle(salleOpt.get());
        
        return surveillanceRepository.save(surveillance);
    }
    
    @Transactional
    public void supprimerSurveillance(Long surveillanceId) {
        surveillanceRepository.deleteById(surveillanceId);
    }
}
