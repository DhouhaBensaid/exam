package com.gestionexamens.repository;

import com.gestionexamens.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialiteRepository extends JpaRepository<Specialite, Long> {
    
    Optional<Specialite> findByCode(String code);
    
    boolean existsByCode(String code);
}
