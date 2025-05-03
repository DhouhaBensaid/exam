package com.gestionexamens.repository;

import com.gestionexamens.model.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    
    Optional<Enseignant> findByMatricule(String matricule);
    
    List<Enseignant> findBySpecialite(String specialite);
    
    @Query("SELECT e FROM Enseignant e JOIN e.surveillances s WHERE s.examen.id = :examenId")
    List<Enseignant> findByExamenId(@Param("examenId") Long examenId);
    
    @Query("SELECT e FROM Enseignant e WHERE " +
           "LOWER(e.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.matricule) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Enseignant> searchEnseignants(@Param("searchTerm") String searchTerm);
}
