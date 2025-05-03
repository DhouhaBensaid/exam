package com.gestionexamens.repository;

import com.gestionexamens.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {

    Optional<Presence> findByEtudiantIdAndExamenIdAndSalleId(Long etudiantId, Long examenId, Long salleId);

    @Query("SELECT new map(e.matricule as matricule, e.nom as nom, e.prenom as prenom) " +
            "FROM Presence p JOIN p.etudiant e " +
            "WHERE p.examen.id = :examenId AND p.salle.id = :salleId")
    List<Map<String, Object>> findEtudiantsPourEmargement(@Param("examenId") Long examenId, @Param("salleId") Long salleId);

    @Query("SELECT e.id, m.nom, ex.dateExamen, ex.heureDebut, ex.heureFin, s.nom, p.present " +
            "FROM Presence p " +
            "JOIN p.etudiant e " +
            "JOIN p.examen ex " +
            "JOIN ex.matiere m " +
            "JOIN p.salle s " +
            "WHERE p.etudiant.id = :etudiantId " +
            "ORDER BY ex.dateExamen, ex.heureDebut")
    List<Object[]> findPresencesByEtudiantId(@Param("etudiantId") Long etudiantId);

    @Query("SELECT e.id, e.matricule, e.nom, e.prenom, a.place, p.present " +
            "FROM Presence p " +
            "JOIN p.etudiant e " +
            "LEFT JOIN Affectation a ON (a.etudiant.id = e.id AND a.examen.id = p.examen.id AND a.salle.id = p.salle.id) " +
            "WHERE p.examen.id = :examenId AND p.salle.id = :salleId " +
            "ORDER BY e.nom, e.prenom")
    List<Object[]> findPresencesByExamenIdAndSalleId(@Param("examenId") Long examenId, @Param("salleId") Long salleId);

    @Query("SELECT e.id, e.matricule, e.nom, e.prenom, ex.id, m.nom, ex.dateExamen, ex.heureDebut, ex.heureFin " +
            "FROM Presence p " +
            "JOIN p.etudiant e " +
            "JOIN p.examen ex " +
            "JOIN ex.matiere m " +
            "WHERE p.salle.id = :salleId AND p.present = false " +
            "ORDER BY e.nom, e.prenom, ex.dateExamen, ex.heureDebut")
    List<Object[]> findAbsencesBySalleId(@Param("salleId") Long salleId);
}