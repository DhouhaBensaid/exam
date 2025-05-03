package com.gestionexamens.service;

import com.gestionexamens.model.Matiere;
import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import com.gestionexamens.repository.MatiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatiereService {
    
    private final MatiereRepository matiereRepository;
    
    @Autowired
    public MatiereService(MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
    }
    
    public List<Matiere> findAll() {
        return matiereRepository.findAll();
    }
    
    public Optional<Matiere> findById(Long id) {
        return matiereRepository.findById(id);
    }
    
    public Optional<Matiere> findByCode(String code) {
        return matiereRepository.findByCode(code);
    }
    
    public List<Matiere> findBySpecialite(Specialite specialite) {
        return matiereRepository.findBySpecialite(specialite);
    }
    
    public List<Matiere> findByNiveau(Niveau niveau) {
        return matiereRepository.findByNiveau(niveau);
    }
    
    public Matiere save(Matiere matiere) {
        return matiereRepository.save(matiere);
    }
    
    public void deleteById(Long id) {
        matiereRepository.deleteById(id);
    }
}
