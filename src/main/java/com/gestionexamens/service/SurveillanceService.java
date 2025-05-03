package com.gestionexamens.service;

import com.gestionexamens.model.Enseignant;
import com.gestionexamens.model.Surveillance;
import com.gestionexamens.repository.SurveillanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SurveillanceService {
    
    private final SurveillanceRepository surveillanceRepository;
    
    @Autowired
    public SurveillanceService(SurveillanceRepository surveillanceRepository) {
        this.surveillanceRepository = surveillanceRepository;
    }
    
    public List<Surveillance> findAll() {
        return surveillanceRepository.findAll();
    }
    
    public Optional<Surveillance> findById(Long id) {
        return surveillanceRepository.findById(id);
    }
    
    public Optional<Surveillance> findByEnseignantIdAndExamenIdAndSalleId(Long enseignantId, Long examenId, Long salleId) {
        return surveillanceRepository.findByEnseignantIdAndExamenIdAndSalleId(enseignantId, examenId, salleId);
    }
    
    public List<Surveillance> findByEnseignantId(Long enseignantId) {
        return surveillanceRepository.findByEnseignantId(enseignantId);
    }
    
    public List<Surveillance> findByExamenId(Long examenId) {
        return surveillanceRepository.findByExamenId(examenId);
    }
    
    public List<Surveillance> findBySalleId(Long salleId) {
        return surveillanceRepository.findBySalleId(salleId);
    }
    
    public List<Surveillance> findByDate(LocalDate date) {
        return surveillanceRepository.findByDate(date);
    }
    
    public List<Surveillance> findByEnseignantIdAndDate(Long enseignantId, LocalDate date) {
        return surveillanceRepository.findByEnseignantIdAndDate(enseignantId, date);
    }
    
    public List<Surveillance> findUpcomingSurveillancesByEnseignantId(Long enseignantId) {
        return surveillanceRepository.findUpcomingSurveillancesByEnseignantId(enseignantId);
    }
    
    public Surveillance save(Surveillance surveillance) {
        return surveillanceRepository.save(surveillance);
    }
    
    public void deleteById(Long id) {
        surveillanceRepository.deleteById(id);
    }


    public List<Surveillance> findByEnseignantAndDate(Enseignant enseignant, LocalDate date) {
        return surveillanceRepository.findByEnseignantAndDate(enseignant, date);
    }

    public List<Surveillance> findByEnseignant(Enseignant enseignant) {
        return surveillanceRepository.findByEnseignant(enseignant);
    }
}
