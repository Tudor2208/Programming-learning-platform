package com.example.proiect.service.impl;

import com.example.proiect.model.Notification;
import com.example.proiect.repository.NotificationRepository;
import com.example.proiect.service.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repo;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationServiceImpl(NotificationRepository repo, SimpMessagingTemplate simpMessagingTemplate) {
        this.repo = repo;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Override
    public Notification createNotification(Notification notification) {
        this.simpMessagingTemplate.convertAndSend("/topic/socket/notification"+notification.getUser().getUsername(), "You have a new notification!");
        return repo.save(notification);
    }

    @Override
    public List<Notification> findAllNotifications() {
        return (List<Notification>)repo.findAll();
    }

    @Override
    public Notification findNotificationById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Notification updateNotification(Long id, Notification notification) {
        Notification notificationToUpdate = repo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("Notification with ID %d doesn't exist", id)));
        notificationToUpdate.setText(notification.getText());
        notificationToUpdate.setUser(notification.getUser());
        notificationToUpdate.setSendingDate(notification.getSendingDate());
        repo.save(notificationToUpdate);
        return notificationToUpdate;
    }

    @Override
    public Notification deleteNotification(Long id) {
        Notification notification = repo.findById(id).get();
        repo.delete(notification);
        return notification;
    }

    @Override
    public List<Notification> getAllUserNotifications(Long id) {
        return repo.getAllByUserId(id);
    }
}
