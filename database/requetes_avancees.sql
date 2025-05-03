-- 1. Nombre d'examens par spécialité et par niveau
SELECT s.nom AS specialite, n.nom AS niveau, COUNT(e.id) AS nombre_examens
FROM examen e
JOIN matiere m ON e.matiere_id = m.id
JOIN specialite s ON m.specialite_id = s.id
JOIN niveau n ON m.niveau_id = n.id
GROUP BY s.nom, n.nom
ORDER BY s.nom, n.nom;

-- 2. Liste des salles avec leur taux d'occupation (nombre d'examens / capacité)
SELECT s.nom AS salle, s.capacite, COUNT(es.examen_id) AS nombre_examens,
       COUNT(es.examen_id) / s.capacite * 100 AS taux_occupation
FROM salle s
LEFT JOIN examen_salle es ON s.id = es.salle_id
GROUP BY s.id, s.nom, s.capacite
ORDER BY taux_occupation DESC;

-- 3. Étudiants qui ont plus de 3 examens dans la même journée
SELECT e.matricule, e.nom, e.prenom, ex.date_examen, COUNT(ae.examen_id) AS nombre_examens
FROM etudiant e
JOIN affectation_etudiant ae ON e.id = ae.etudiant_id
JOIN examen ex ON ae.examen_id = ex.id
GROUP BY e.id, e.matricule, e.nom, e.prenom, ex.date_examen
HAVING COUNT(ae.examen_id) > 3
ORDER BY ex.date_examen, e.nom, e.prenom;

-- 4. Enseignants qui surveillent le plus d'examens
SELECT en.matricule, en.nom, en.prenom, COUNT(s.id) AS nombre_surveillances
FROM enseignant en
JOIN surveillance s ON en.id = s.enseignant_id
GROUP BY en.id, en.matricule, en.nom, en.prenom
ORDER BY nombre_surveillances DESC
LIMIT 10;

-- 5. Examens sans surveillants assignés
SELECT m.code AS code_matiere, m.nom AS matiere, ex.date_examen, ex.heure_debut, ex.heure_fin,
       s.nom AS salle
FROM examen ex
JOIN matiere m ON ex.matiere_id = m.id
JOIN examen_salle es ON ex.id = es.examen_id
JOIN salle s ON es.salle_id = s.id
LEFT JOIN surveillance surv ON ex.id = surv.examen_id AND es.salle_id = surv.salle_id
WHERE surv.id IS NULL
ORDER BY ex.date_examen, ex.heure_debut;

-- 6. Taux de présence aux examens par niveau et spécialité
SELECT s.nom AS specialite, n.nom AS niveau, 
       COUNT(ae.id) AS total_affectations,
       SUM(CASE WHEN ae.present = 1 THEN 1 ELSE 0 END) AS presents,
       (SUM(CASE WHEN ae.present = 1 THEN 1 ELSE 0 END) / COUNT(ae.id)) * 100 AS taux_presence
FROM affectation_etudiant ae
JOIN etudiant e ON ae.etudiant_id = e.id
JOIN specialite s ON e.specialite_id = s.id
JOIN niveau n ON e.niveau_id = n.id
GROUP BY s.nom, n.nom
ORDER BY taux_presence DESC;

-- 7. Répartition des étudiants par salle pour un examen spécifique
SELECT ex.id AS examen_id, m.nom AS matiere, s.nom AS salle, s.capacite,
       COUNT(ae.etudiant_id) AS nombre_etudiants,
       s.capacite - COUNT(ae.etudiant_id) AS places_restantes
FROM examen ex
JOIN matiere m ON ex.matiere_id = m.id
JOIN examen_salle es ON ex.id = es.examen_id
JOIN salle s ON es.salle_id = s.id
LEFT JOIN affectation_etudiant ae ON ex.id = ae.examen_id AND s.id = ae.salle_id
WHERE ex.id = 1 -- Remplacer par l'ID de l'examen souhaité
GROUP BY ex.id, m.nom, s.nom, s.capacite;

-- 8. Nombre d'examens par jour pendant la période d'examen
SELECT ex.date_examen, COUNT(ex.id) AS nombre_examens,
       COUNT(DISTINCT es.salle_id) AS nombre_salles_utilisees
FROM examen ex
JOIN examen_salle es ON ex.id = es.examen_id
GROUP BY ex.date_examen
ORDER BY ex.date_examen;

-- 9. Vue des conflits potentiels (même étudiant affecté à deux examens en même temps)
CREATE OR REPLACE VIEW conflits_examens AS
SELECT e1.matricule, e1.nom, e1.prenom, 
       ex1.date_examen AS date_examen1, ex1.heure_debut AS heure_debut1, ex1.heure_fin AS heure_fin1, m1.nom AS matiere1,
       ex2.date_examen AS date_examen2, ex2.heure_debut AS heure_debut2, ex2.heure_fin AS heure_fin2, m2.nom AS matiere2
FROM etudiant e1
JOIN affectation_etudiant ae1 ON e1.id = ae1.etudiant_id
JOIN examen ex1 ON ae1.examen_id = ex1.id
JOIN matiere m1 ON ex1.matiere_id = m1.id
JOIN affectation_etudiant ae2 ON e1.id = ae2.etudiant_id
JOIN examen ex2 ON ae2.examen_id = ex2.id
JOIN matiere m2 ON ex2.matiere_id = m2.id
WHERE ex1.id < ex2.id
  AND ex1.date_examen = ex2.date_examen
  AND ((ex1.heure_debut <= ex2.heure_debut AND ex1.heure_fin > ex2.heure_debut)
       OR (ex2.heure_debut <= ex1.heure_debut AND ex2.heure_fin > ex1.heure_debut));

-- 10. Procédure stockée pour générer automatiquement les affectations d'étudiants
DELIMITER //
CREATE PROCEDURE generer_affectations_examens(IN p_examen_id INT)
BEGIN
    DECLARE v_matiere_id INT;
    DECLARE v_niveau_id INT;
    DECLARE v_specialite_id INT;
    DECLARE v_salle_id INT;
    DECLARE v_capacite INT;
    DECLARE v_place INT;
    DECLARE v_etudiant_id INT;
    DECLARE done INT DEFAULT FALSE;
    
    -- Récupérer les informations de l'examen
    SELECT matiere_id INTO v_matiere_id FROM examen WHERE id = p_examen_id;
    SELECT niveau_id, specialite_id INTO v_niveau_id, v_specialite_id FROM matiere WHERE id = v_matiere_id;
    
    -- Curseur pour les salles assignées à cet examen
    DECLARE cur_salles CURSOR FOR 
        SELECT es.salle_id, s.capacite 
        FROM examen_salle es
        JOIN salle s ON es.salle_id = s.id
        WHERE es.examen_id = p_examen_id;
    
    -- Curseur pour les étudiants du niveau et de la spécialité concernés
    DECLARE cur_etudiants CURSOR FOR 
        SELECT id FROM etudiant 
        WHERE niveau_id = v_niveau_id AND specialite_id = v_specialite_id
        ORDER BY nom, prenom;
    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    
    -- Supprimer les affectations existantes pour cet examen
    DELETE FROM affectation_etudiant WHERE examen_id = p_examen_id;
    
    -- Ouvrir le curseur des salles
    OPEN cur_salles;
    
    read_salles: LOOP
        FETCH cur_salles INTO v_salle_id, v_capacite;
        IF done THEN
            LEAVE read_salles;
        END IF;
        
        SET v_place = 1;
        SET done = FALSE;
        
        -- Ouvrir le curseur des étudiants
        OPEN cur_etudiants;
        
        read_etudiants: LOOP
            FETCH cur_etudiants INTO v_etudiant_id;
            IF done OR v_place > v_capacite THEN
                LEAVE read_etudiants;
            END IF;
            
            -- Insérer l'affectation
            INSERT INTO affectation_etudiant (etudiant_id, examen_id, salle_id, place, present)
            VALUES (v_etudiant_id, p_examen_id, v_salle_id, CONCAT('P', v_place), FALSE);
            
            SET v_place = v_place + 1;
        END LOOP;
        
        CLOSE cur_etudiants;
        SET done = FALSE;
    END LOOP;
    
    CLOSE cur_salles;
END //
DELIMITER ;
