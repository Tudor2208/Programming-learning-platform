package com.example.proiect.cronjob;

import com.example.proiect.repository.UserRepository;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Component
public class UserRegistrationAlert {

    private final UserRepository userRepository;

    public UserRegistrationAlert(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   // @Scheduled(fixedDelay = 300000, initialDelay = 1000)
    public void showUserRegistrationAlert() {
        Date now = new Date();
        Date fiveMinutesAgo = new Date(now.getTime() - (5 * 60 * 1000));
        int numberOfRegistrations = userRepository.countByRegisterDateBetween(fiveMinutesAgo, now);

        System.out.println("Number of users registered in the last 5 minutes: " + numberOfRegistrations);
    }
}