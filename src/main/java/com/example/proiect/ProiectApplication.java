package com.example.proiect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ProiectApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProiectApplication.class, args);
    }

}
