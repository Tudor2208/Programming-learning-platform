package com.example.proiect.controllers;

import com.example.proiect.model.LoginActivity;
import com.example.proiect.model.Problem;
import com.example.proiect.service.LoginActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/activity")
@Controller
public class LoginActivityController {
    private final LoginActivityService loginActivityService;

    public LoginActivityController(LoginActivityService loginActivityService) {
        this.loginActivityService = loginActivityService;
    }

    @PostMapping
    public ResponseEntity createNewLoginActivity(@RequestBody LoginActivity loginActivity){
        return ResponseEntity.status(HttpStatus.OK).body(loginActivityService.createLoginActivity(loginActivity));
    }


    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loginActivityService.findLoginActivityById(id));
    }

    @GetMapping
    public ResponseEntity findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(loginActivityService.findAllLoginActivities());
    }

    @GetMapping("/connected")
    public ResponseEntity countConnectedUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(loginActivityService.findAllLoggedInUsers().size());
    }

    @GetMapping("/is-logged/{id}")
    public ResponseEntity isLoggedIn(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(loginActivityService.isUserLoggedIn(id));
    }




}
