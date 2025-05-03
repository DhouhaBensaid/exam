package com.gestionexamens.repository;

import com.gestionexamens.model.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    
    Optional<Affectation> findByEtudiantIdAndExamenId(Long etudiantId, Long examenId);
    
    List<Affectation> findByEtudiantId(Long etudiantId);
    
    List<Affectation> findByExamenId(Long examenId);
    
    List<Affectation> findBySalleId(Long salleId);
    
    List<Affectation> findByExamenIdAndSalleId(Long examenId, Long salleId);
    
    @Query("SELECT a FROM Affectation a WHERE a.examen.id = :examenId ORDER BY a.salle.nom, a.place")
    List<Affectation> findByExamenIdOrderBySalleAndPlace(@Param("examenId") Long examenId);
}
