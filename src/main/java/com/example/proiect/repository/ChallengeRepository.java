package com.example.proiect.repository;

import com.example.proiect.model.Challenge;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ChallengeStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChallengeRepository extends CrudRepository<Challenge, Long> {
    List<Challenge> findAllByStatus(ChallengeStatus status);
    List<Challenge> findAllByStatusAndChallengedUser(ChallengeStatus status, User user);
    List<Challenge> findAllByStatusAndChallengingUser(ChallengeStatus status, User user);
}
