package com.gestionexamens.service;

import com.gestionexamens.model.Etudiant;
import com.gestionexamens.model.Niveau;
import com.gestionexamens.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtudiantService {
    
    private final EtudiantRepository etudiantRepository;
    
    @Autowired
    public EtudiantService(EtudiantRepository etudiantRepository) {
        this.etudiantRepository = etudiantRepository;
    }
    
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }
    
    public Optional<Etudiant> findById(Long id) {
        return etudiantRepository.findById(id);
    }
    
    public Optional<Etudiant> findByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule);
    }
    
    public List<Etudiant> findByNiveau(Niveau niveau) {
        return etudiantRepository.findByNiveau(niveau);
    }
    
    public List<Etudiant> searchEtudiants(String searchTerm) {
        return etudiantRepository.searchEtudiants(searchTerm);
    }
    
    public List<Etudiant> findBySpecialiteId(Long specialiteId) {
        return etudiantRepository.findBySpecialiteId(specialiteId);
    }
    
    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }
    
    public void deleteById(Long id) {
        etudiantRepository.deleteById(id);
    }
}
