package com.example.proiect.controllers;

import com.example.proiect.model.User;
import com.example.proiect.model.UserSubscriber;
import com.example.proiect.service.UserService;
import com.example.proiect.service.impl.UserSubscriberServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/newsletter")
public class UserSubscriberController {

    private final UserSubscriberServiceImpl subscriberService;
    private final UserService userService;

    public UserSubscriberController(UserSubscriberServiceImpl subscriberService, UserService userService) {
        this.subscriberService = subscriberService;
        this.userService = userService;
    }

    @GetMapping("/is-subscribed/{id}")
    public ResponseEntity findAllTests(@PathVariable Long id) {
        User myUser = userService.findUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(subscriberService.existsUserSubscriber(myUser));
    }

    @DeleteMapping("/unsubscribe/{id}")
    public ResponseEntity unsubscribe(@PathVariable Long id) {
        Long subsId = subscriberService.findSubscriberByUserId(id);
        subscriberService.deleteUserSubscriber(subsId);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/subscribe/{id}")
    public ResponseEntity subscribe(@PathVariable Long id) {
        UserSubscriber subscriber = new UserSubscriber(userService.findUserById(id));
        subscriber.setSubscribtionDate(new Date());
        subscriberService.createUserSubscriber(subscriber);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
