package com.gestionexamens.repository;

import com.gestionexamens.model.Etudiant;
import com.gestionexamens.model.Niveau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    
    Optional<Etudiant> findByMatricule(String matricule);
    
    List<Etudiant> findByNiveau(Niveau niveau);
    
    @Query("SELECT e FROM Etudiant e WHERE " +
           "LOWER(e.nom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.prenom) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(e.matricule) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Etudiant> searchEtudiants(@Param("searchTerm") String searchTerm);
    
    @Query("SELECT e FROM Etudiant e WHERE e.niveau.specialite.id = :specialiteId")
    List<Etudiant> findBySpecialiteId(@Param("specialiteId") Long specialiteId);
}
