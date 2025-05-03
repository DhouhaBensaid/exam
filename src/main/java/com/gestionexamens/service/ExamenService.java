package com.gestionexamens.service;

import com.gestionexamens.model.Examen;
import com.gestionexamens.model.Matiere;
import com.gestionexamens.repository.ExamenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenService {
    
    private final ExamenRepository examenRepository;
    
    @Autowired
    public ExamenService(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }
    
    public List<Examen> findAll() {
        return examenRepository.findAll();
    }
    
    public Optional<Examen> findById(Long id) {
        return examenRepository.findById(id);
    }
    
    public List<Examen> findByMatiere(Matiere matiere) {
        return examenRepository.findByMatiere(matiere);
    }
    
    public List<Examen> findByDateExamen(LocalDate dateExamen) {
        return examenRepository.findByDateExamen(dateExamen);
    }
    
    public List<Examen> findByDateExamenBetween(LocalDate dateDebut, LocalDate dateFin) {
        return examenRepository.findByDateExamenBetween(dateDebut, dateFin);
    }
    
    public List<Examen> findByAnneeAcademique(String anneeAcademique) {
        return examenRepository.findByAnneeAcademique(anneeAcademique);
    }
    
    public List<Examen> findByNiveauId(Long niveauId) {
        return examenRepository.findByNiveauId(niveauId);
    }
    
    public List<Examen> findBySpecialiteId(Long specialiteId) {
        return examenRepository.findBySpecialiteId(specialiteId);
    }
    
    public List<Examen> findUpcomingExamens() {
        return examenRepository.findUpcomingExamens();
    }
    
    public Examen save(Examen examen) {
        return examenRepository.save(examen);
    }
    
    public void deleteById(Long id) {
        examenRepository.deleteById(id);
    }
}
