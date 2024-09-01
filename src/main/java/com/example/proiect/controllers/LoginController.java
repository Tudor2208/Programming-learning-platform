package com.example.proiect.controllers;

import com.example.proiect.dto.AuthDTO;
import com.example.proiect.model.LoginActivity;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ActivityType;
import com.example.proiect.service.LoginActivityService;
import com.example.proiect.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserService userService;
    private final LoginActivityService loginActivityService;
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, LoginActivityService loginActivityService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.loginActivityService = loginActivityService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody AuthDTO auth){
        User user = userService.findUserByUsername(auth.getUsername());
        if(user != null) {
            boolean matches = passwordEncoder.matches(auth.getPassword(), user.getPassword());
            if(!matches) return ResponseEntity.status(HttpStatus.OK).body(null);
            LoginActivity loginActivity = LoginActivity.builder()
                    .activityType(ActivityType.LOGIN)
                    .user(user)
                    .date(new Date())
                    .build();
            loginActivityService.createLoginActivity(loginActivity);
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
