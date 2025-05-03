package com.gestionexamens.service;

import com.gestionexamens.model.Affectation;
import com.gestionexamens.model.Etudiant;
import com.gestionexamens.model.Examen;
import com.gestionexamens.model.Salle;
import com.gestionexamens.repository.AffectationRepository;
import com.gestionexamens.repository.EtudiantRepository;
import com.gestionexamens.repository.ExamenRepository;
import com.gestionexamens.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AffectationService {
    
    private final AffectationRepository affectationRepository;
    private final EtudiantRepository etudiantRepository;
    private final ExamenRepository examenRepository;
    private final SalleRepository salleRepository;
    
    @Autowired
    public AffectationService(
            AffectationRepository affectationRepository,
            EtudiantRepository etudiantRepository,
            ExamenRepository examenRepository,
            SalleRepository salleRepository) {
        this.affectationRepository = affectationRepository;
        this.etudiantRepository = etudiantRepository;
        this.examenRepository = examenRepository;
        this.salleRepository = salleRepository;
    }
    
    public List<Affectation> findAll() {
        return affectationRepository.findAll();
    }
    
    public Optional<Affectation> findById(Long id) {
        return affectationRepository.findById(id);
    }
    
    public Optional<Affectation> findByEtudiantIdAndExamenId(Long etudiantId, Long examenId) {
        return affectationRepository.findByEtudiantIdAndExamenId(etudiantId, examenId);
    }
    
    public List<Affectation> findByEtudiantId(Long etudiantId) {
        return affectationRepository.findByEtudiantId(etudiantId);
    }
    
    public List<Affectation> findByExamenId(Long examenId) {
        return affectationRepository.findByExamenId(examenId);
    }
    
    public List<Affectation> findBySalleId(Long salleId) {
        return affectationRepository.findBySalleId(salleId);
    }
    
    public List<Affectation> findByExamenIdAndSalleId(Long examenId, Long salleId) {
        return affectationRepository.findByExamenIdAndSalleId(examenId, salleId);
    }
    
    public List<Affectation> findByExamenIdOrderBySalleAndPlace(Long examenId) {
        return affectationRepository.findByExamenIdOrderBySalleAndPlace(examenId);
    }
    
    @Transactional
    public Affectation save(Affectation affectation) {
        return affectationRepository.save(affectation);
    }
    
    @Transactional
    public Affectation affecterEtudiant(Long etudiantId, Long examenId, Long salleId, Integer place) {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        Optional<Examen> examenOpt = examenRepository.findById(examenId);
        Optional<Salle> salleOpt = salleRepository.findById(salleId);
        
        if (etudiantOpt.isEmpty() || examenOpt.isEmpty() || salleOpt.isEmpty()) {
            throw new IllegalArgumentException("Étudiant, examen ou salle non trouvé");
        }
        
        Affectation affectation = affectationRepository
                .findByEtudiantIdAndExamenId(etudiantId, examenId)
                .orElse(new Affectation());
        
        affectation.setEtudiant(etudiantOpt.get());
        affectation.setExamen(examenOpt.get());
        affectation.setSalle(salleOpt.get());
        affectation.setPlace(place);
        
        return affectationRepository.save(affectation);
    }
    
    @Transactional
    public void deleteById(Long id) {
        affectationRepository.deleteById(id);
    }
}
