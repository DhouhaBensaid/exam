package com.gestionexamens.repository;

import com.gestionexamens.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    List<Notification> findByUserIdOrderByDateCreationDesc(Long userId);
    
    List<Notification> findByUserIdAndLueOrderByDateCreationDesc(Long userId, boolean lue);
    
    long countByUserIdAndLue(Long userId, boolean lue);
}
