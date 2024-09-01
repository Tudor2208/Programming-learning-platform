package com.example.proiect.controllers;

import com.example.proiect.exceptions.ApiExceptionResponse;
import com.example.proiect.model.Challenge;
import com.example.proiect.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@CrossOrigin
@RequestMapping("/challenge")
public class ChallengeController {
    private final ChallengeService challengeService;

    public ChallengeController(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @PostMapping
    public ResponseEntity createChallenge(@RequestBody Challenge challenge) throws ApiExceptionResponse {
        challenge.setCreateTime(new Date());
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.createChallenge(challenge));
    }

    @GetMapping("/pending/{id}")
    public ResponseEntity findUserPendingChallanges(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.findUserPendingChallenges(id));
    }

    @GetMapping("/ongoing/{id}")
    public ResponseEntity findUserOngoingChallanges(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.findUserOngoingChallenges(id));
    }

    @GetMapping("/completed/{id}")
    public ResponseEntity findUserCompletedChallanges(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.findUserCompletedChallenges(id));
    }

    @PutMapping("/accept/{id}")
    public ResponseEntity acceptChallenge(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(challengeService.acceptChallenge(id));
    }

}
