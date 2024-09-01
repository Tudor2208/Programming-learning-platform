package com.example.proiect.service.impl;

import com.example.proiect.model.LoginActivity;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ActivityType;
import com.example.proiect.repository.LoginActivityRepository;
import com.example.proiect.service.LoginActivityService;
import com.example.proiect.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class LoginActivityServiceImpl implements LoginActivityService {

    private final LoginActivityRepository loginActivityRepository;
    private final UserService userService;

    public LoginActivityServiceImpl(LoginActivityRepository loginActivityRepository, UserService userService) {
        this.loginActivityRepository = loginActivityRepository;
        this.userService = userService;
    }

    @Override
    public LoginActivity createLoginActivity(LoginActivity loginActivity) {
        return loginActivityRepository.save(loginActivity);
    }

    @Override
    public LoginActivity findLoginActivityById(Long id) {
        return loginActivityRepository.findById(id).get();
    }

    @Override
    public List<LoginActivity> findAllLoginActivities() {
        return (List<LoginActivity>) loginActivityRepository.findAll();
    }

    @Override
    public boolean isUserLoggedIn(Long userId) {
        User user = userService.findUserById(userId);
        List<LoginActivity> userAct = loginActivityRepository.findAllByUser(user);
        if(!userAct.isEmpty()) {
            LoginActivity mostRecentActivity = Collections.max(userAct, Comparator.comparing(LoginActivity::getDate));
            return mostRecentActivity.getActivityType() == ActivityType.LOGIN;
        }
       return false;
    }

    @Override
    public List<User> findAllLoggedInUsers() {
        List<User> allUsers = userService.findAllUsers();
        List<User> loggedIn = new ArrayList<>();
        for (User user : allUsers) {
            if(isUserLoggedIn(user.getId())) {
                loggedIn.add(user);
            }
        }
        return loggedIn;
    }


}
