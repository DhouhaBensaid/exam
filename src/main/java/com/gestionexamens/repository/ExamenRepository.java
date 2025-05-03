package com.gestionexamens.repository;

import com.gestionexamens.model.Examen;
import com.gestionexamens.model.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamenRepository extends JpaRepository<Examen, Long> {
    
    List<Examen> findByMatiere(Matiere matiere);
    
    List<Examen> findByDateExamen(LocalDate dateExamen);
    
    List<Examen> findByDateExamenBetween(LocalDate dateDebut, LocalDate dateFin);
    
    List<Examen> findByAnneeAcademique(String anneeAcademique);
    
    @Query("SELECT e FROM Examen e WHERE e.matiere.niveau.id = :niveauId")
    List<Examen> findByNiveauId(@Param("niveauId") Long niveauId);
    
    @Query("SELECT e FROM Examen e WHERE e.matiere.specialite.id = :specialiteId")
    List<Examen> findBySpecialiteId(@Param("specialiteId") Long specialiteId);
    
    @Query("SELECT e FROM Examen e WHERE e.dateExamen >= CURRENT_DATE ORDER BY e.dateExamen, e.heureDebut")
    List<Examen> findUpcomingExamens();
}
