package com.gestionexamens.repository;

import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NiveauRepository extends JpaRepository<Niveau, Long> {
    
    Optional<Niveau> findByCode(String code);
    
    List<Niveau> findBySpecialite(Specialite specialite);
    
    List<Niveau> findBySpecialiteAndAnneeAcademique(Specialite specialite, String anneeAcademique);
}
