package com.gestionexamens.repository;

import com.gestionexamens.model.Matiere;
import com.gestionexamens.model.Niveau;
import com.gestionexamens.model.Specialite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    
    Optional<Matiere> findByCode(String code);
    
    List<Matiere> findBySpecialite(Specialite specialite);
    
    List<Matiere> findByNiveau(Niveau niveau);
}
