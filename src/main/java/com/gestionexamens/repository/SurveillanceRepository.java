package com.gestionexamens.repository;

import com.gestionexamens.model.Enseignant;
import com.gestionexamens.model.Surveillance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SurveillanceRepository extends JpaRepository<Surveillance, Long> {

    Optional<Surveillance> findByEnseignantIdAndExamenIdAndSalleId(Long enseignantId, Long examenId, Long salleId);

    List<Surveillance> findByEnseignantId(Long enseignantId);

    List<Surveillance> findByExamenId(Long examenId);

    List<Surveillance> findBySalleId(Long salleId);

    @Query("SELECT s FROM Surveillance s WHERE s.examen.dateExamen = :date")
    List<Surveillance> findByDate(@Param("date") LocalDate date);

    @Query("SELECT s FROM Surveillance s WHERE s.enseignant.id = :enseignantId AND s.examen.dateExamen = :date")
    List<Surveillance> findByEnseignantIdAndDate(@Param("enseignantId") Long enseignantId, @Param("date") LocalDate date);

    @Query("SELECT s FROM Surveillance s WHERE s.enseignant.id = :enseignantId AND s.examen.dateExamen >= CURRENT_DATE ORDER BY s.examen.dateExamen, s.examen.heureDebut")
    List<Surveillance> findUpcomingSurveillancesByEnseignantId(@Param("enseignantId") Long enseignantId);

    List<Surveillance> findByEnseignantAndDate(Enseignant enseignant, LocalDate date);
    List<Surveillance> findByEnseignant(Enseignant enseignant);

}
