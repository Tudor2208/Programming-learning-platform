package com.example.proiect.controllers;

import com.example.proiect.service.impl.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@CrossOrigin
public class EmailController {
    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    @PostMapping("/send/{to}/{subject}/{body}")
    public ResponseEntity sendEmail(@PathVariable String to, @PathVariable String subject, @PathVariable String body) {
        service.sendEmail(to, subject, body);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
