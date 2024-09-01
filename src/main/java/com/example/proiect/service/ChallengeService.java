package com.example.proiect.service;

import com.example.proiect.exceptions.ApiExceptionResponse;
import com.example.proiect.model.Challenge;
import com.example.proiect.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChallengeService {

    Challenge createChallenge(Challenge challenge) throws ApiExceptionResponse;
    List<Challenge> findAllPendingChallenges();
    List<Challenge> findAllOngoingChallenges();
    List<Challenge> findUserOngoingChallenges(Long userId);
    List<Challenge> findUserCompletedChallenges(Long userId);
    List<Challenge> findUserPendingChallenges(Long userId);
    Challenge findById(Long id);
    Challenge acceptChallenge(Long challengeId);

    User getWinner(Long challengeId);
}
