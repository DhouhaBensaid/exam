package com.gestionexamens.service;

import com.gestionexamens.model.Notification;
import com.gestionexamens.model.User;
import com.gestionexamens.repository.NotificationRepository;
import com.gestionexamens.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    
    @Autowired
    public NotificationService(
            NotificationRepository notificationRepository,
            UserRepository userRepository,
            EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    
    public List<Notification> findByUserId(Long userId) {
        return notificationRepository.findByUserIdOrderByDateCreationDesc(userId);
    }
    
    public List<Notification> findByUserIdAndLue(Long userId, boolean lue) {
        return notificationRepository.findByUserIdAndLueOrderByDateCreationDesc(userId, lue);
    }
    
    public long countUnreadByUserId(Long userId) {
        return notificationRepository.countByUserIdAndLue(userId, false);
    }
    
    @Transactional
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public Notification createNotification(Long userId, String titre, String message, String type, String lien) {
        Optional<User> userOpt = userRepository.findById(userId);
        
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé");
        }
        
        User user = userOpt.get();
        
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitre(titre);
        notification.setMessage(message);
        notification.setType(type);
        notification.setLien(lien);
        notification.setDateCreation(LocalDateTime.now());
        notification.setLue(false);
        
        // Envoyer un email si l'utilisateur a une adresse email
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            emailService.sendSimpleMessage(
                    user.getEmail(),
                    "Notification: " + titre,
                    message
            );
        }
        
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public Notification marquerCommeLue(Long notificationId) {
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationId);
        
        if (notificationOpt.isEmpty()) {
            throw new IllegalArgumentException("Notification non trouvée");
        }
        
        Notification notification = notificationOpt.get();
        notification.setLue(true);
        notification.setDateLecture(LocalDateTime.now());
        
        return notificationRepository.save(notification);
    }
    
    @Transactional
    public void marquerToutesCommeLues(Long userId) {
        List<Notification> notifications = notificationRepository.findByUserIdAndLueOrderByDateCreationDesc(userId, false);
        
        for (Notification notification : notifications) {
            notification.setLue(true);
            notification.setDateLecture(LocalDateTime.now());
            notificationRepository.save(notification);
        }
    }
    
    @Transactional
    public void deleteById(Long id) {
        notificationRepository.deleteById(id);
    }
}
