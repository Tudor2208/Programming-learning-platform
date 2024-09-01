package com.example.proiect.controllers;

import com.example.proiect.service.impl.NotificationServiceImpl;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationServiceImpl service;

    public NotificationController(NotificationServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getAllUserNotif(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUserNotifications(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNotification(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteNotification(id));
    }
}
