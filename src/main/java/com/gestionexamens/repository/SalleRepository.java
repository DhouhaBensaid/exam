package com.gestionexamens.repository;

import com.gestionexamens.model.Salle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
    
    Optional<Salle> findByCode(String code);
    
    List<Salle> findByCapaciteGreaterThanEqual(Integer capacite);
    
    List<Salle> findByDisponible(Boolean disponible);
    
    @Query("SELECT s FROM Salle s WHERE s.disponible = true AND s.id NOT IN " +
           "(SELECT DISTINCT a.salle.id FROM Affectation a " +
           "JOIN a.examen e " +
           "WHERE e.dateExamen = :date AND " +
           "((e.heureDebut <= :heureFin AND e.heureFin >= :heureDebut) OR " +
           "(e.heureDebut >= :heureDebut AND e.heureDebut < :heureFin)))")
    List<Salle> findAvailableSalles(
            @Param("date") LocalDate date,
            @Param("heureDebut") LocalTime heureDebut,
            @Param("heureFin") LocalTime heureFin);
    
    @Query("SELECT s FROM Salle s WHERE s.specialite.id = :specialiteId")
    List<Salle> findSallesBySpecialite(@Param("specialiteId") Long specialiteId);
}
