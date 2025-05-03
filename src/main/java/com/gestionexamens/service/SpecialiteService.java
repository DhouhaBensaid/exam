package com.gestionexamens.service;

import com.gestionexamens.model.Specialite;
import com.gestionexamens.repository.SpecialiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpecialiteService {
    
    private final SpecialiteRepository specialiteRepository;
    
    @Autowired
    public SpecialiteService(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }
    
    public List<Specialite> findAll() {
        return specialiteRepository.findAll();
    }
    
    public Optional<Specialite> findById(Long id) {
        return specialiteRepository.findById(id);
    }
    
    public Optional<Specialite> findByCode(String code) {
        return specialiteRepository.findByCode(code);
    }
    
    public Specialite save(Specialite specialite) {
        return specialiteRepository.save(specialite);
    }
    
    public void deleteById(Long id) {
        specialiteRepository.deleteById(id);
    }
    
    public boolean existsByCode(String code) {
        return specialiteRepository.existsByCode(code);
    }
}
