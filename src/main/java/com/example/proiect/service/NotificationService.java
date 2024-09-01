package com.example.proiect.service;

import com.example.proiect.model.Notification;
import com.example.proiect.model.ProblemCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface NotificationService {

    Notification createNotification(Notification notification);
    List<Notification> findAllNotifications();
    Notification findNotificationById(Long id);
    Notification updateNotification(Long id, Notification notification);
    Notification deleteNotification(Long id);

    List<Notification> getAllUserNotifications(Long id);
}
