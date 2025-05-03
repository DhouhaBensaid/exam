package com.gestionexamens.service;

import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import com.gestionexamens.repository.NiveauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NiveauService {
    
    private final NiveauRepository niveauRepository;
    
    @Autowired
    public NiveauService(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }
    
    public List<Niveau> findAll() {
        return niveauRepository.findAll();
    }
    
    public Optional<Niveau> findById(Long id) {
        return niveauRepository.findById(id);
    }
    
    public Optional<Niveau> findByCode(String code) {
        return niveauRepository.findByCode(code);
    }
    
    public List<Niveau> findBySpecialite(Specialite specialite) {
        return niveauRepository.findBySpecialite(specialite);
    }
    
    public List<Niveau> findBySpecialiteAndAnneeAcademique(Specialite specialite, String anneeAcademique) {
        return niveauRepository.findBySpecialiteAndAnneeAcademique(specialite, anneeAcademique);
    }
    
    public Niveau save(Niveau niveau) {
        return niveauRepository.save(niveau);
    }
    
    public void deleteById(Long id) {
        niveauRepository.deleteById(id);
    }
}
