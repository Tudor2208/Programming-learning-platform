package com.example.proiect.service;

import com.example.proiect.model.LoginActivity;
import com.example.proiect.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LoginActivityService {
    LoginActivity createLoginActivity(LoginActivity loginActivity);
    LoginActivity findLoginActivityById(Long id);
    List<LoginActivity> findAllLoginActivities();
    boolean isUserLoggedIn(Long userId);
    List<User> findAllLoggedInUsers();

}
