package com.gestionexamens.service;

import com.gestionexamens.model.Salle;
import com.gestionexamens.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SalleService {
    
    private final SalleRepository salleRepository;
    
    @Autowired
    public SalleService(SalleRepository salleRepository) {
        this.salleRepository = salleRepository;
    }
    
    public List<Salle> findAll() {
        return salleRepository.findAll();
    }
    
    public Optional<Salle> findById(Long id) {
        return salleRepository.findById(id);
    }
    
    public Optional<Salle> findByCode(String code) {
        return salleRepository.findByCode(code);
    }
    
    public List<Salle> findByCapaciteGreaterThanEqual(Integer capacite) {
        return salleRepository.findByCapaciteGreaterThanEqual(capacite);
    }
    
    public List<Salle> findByDisponible(Boolean disponible) {
        return salleRepository.findByDisponible(disponible);
    }
    
    public List<Salle> findAvailableSalles(LocalDate date, LocalTime heureDebut, LocalTime heureFin) {
        return salleRepository.findAvailableSalles(date, heureDebut, heureFin);
    }
    
    public List<Salle> findSallesBySpecialite(Long specialiteId) {
        return salleRepository.findSallesBySpecialite(specialiteId);
    }
    
    public Salle save(Salle salle) {
        return salleRepository.save(salle);
    }
    
    public void deleteById(Long id) {
        salleRepository.deleteById(id);
    }
}
