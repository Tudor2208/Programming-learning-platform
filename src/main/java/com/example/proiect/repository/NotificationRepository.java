package com.example.proiect.repository;

import com.example.proiect.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    List<Notification> getAllByUserId(Long id);
}
