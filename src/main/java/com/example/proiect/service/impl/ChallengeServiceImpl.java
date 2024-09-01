package com.example.proiect.service.impl;

import com.example.proiect.exceptions.ApiExceptionResponse;
import com.example.proiect.model.Challenge;
import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemSolution;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ChallengeStatus;
import com.example.proiect.repository.ChallengeRepository;
import com.example.proiect.repository.ProblemSolutionRepository;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.ChallengeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ChallengeServiceImpl implements ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ProblemSolutionRepository problemSolutionRepository;


    public ChallengeServiceImpl(ChallengeRepository challengeRepository, UserRepository userRepository, ProblemSolutionRepository problemSolutionRepository) {
        this.challengeRepository = challengeRepository;
        this.userRepository = userRepository;
        this.problemSolutionRepository = problemSolutionRepository;
    }

    @Override
    public Challenge createChallenge(Challenge challenge) throws ApiExceptionResponse {
        User challengingUser = challenge.getChallengingUser();
        User challengedUser = challenge.getChallengedUser();

        List<ProblemSolution> solutionsUser1 = problemSolutionRepository.findAllByProblemAndUser(challenge.getProblem(), challengedUser);
        List<ProblemSolution> solutionsUser2 = problemSolutionRepository.findAllByProblemAndUser(challenge.getProblem(), challengingUser);

        if(!solutionsUser1.isEmpty()) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("Challenged user has solutions to that problem!").build();
        }

        if(!solutionsUser2.isEmpty()) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("You already have solutions to that problem!").build();
        }

         if(challenge.getPoints() > 500 || challenge.getPoints() < 10) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("Invalid number of points! [between 10 and 500]").build();
        }

        if(challenge.getMinutes() > 120 || challenge.getMinutes() < 1) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("Invalid number of minutes! [between 1 and 120]").build();
        }

        if(challengingUser.getPoints() < challenge.getPoints()) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("You don't have enough points to challenge that user!").build();
        }

        if(challengedUser.getPoints() < challenge.getPoints()) {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("Challenged user doesn't have enough points!").build();
        }

        return challengeRepository.save(challenge);
    }

    @Override
    public List<Challenge> findAllPendingChallenges() {
        return challengeRepository.findAllByStatus(ChallengeStatus.PENDING);
    }

    @Override
    public List<Challenge> findAllOngoingChallenges() {
        return challengeRepository.findAllByStatus(ChallengeStatus.ONGOING);
    }

    @Override
    public List<Challenge> findUserPendingChallenges(Long userId) {
        User user = userRepository.findById(userId).get();
        return challengeRepository.findAllByStatusAndChallengedUser(ChallengeStatus.PENDING, user);
    }

    @Override
    public List<Challenge> findUserOngoingChallenges(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Challenge> allOngoing = challengeRepository.findAllByStatusAndChallengedUser(ChallengeStatus.ONGOING, user);
        List<Challenge> allByStatusAndChallengingUser = challengeRepository.findAllByStatusAndChallengingUser(ChallengeStatus.ONGOING, user);
        allOngoing.addAll(allByStatusAndChallengingUser);
        return allOngoing;
    }

    @Override
    public List<Challenge> findUserCompletedChallenges(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Challenge> allCompleted = challengeRepository.findAllByStatusAndChallengedUser(ChallengeStatus.COMPLETED, user);
        List<Challenge> allByStatusAndChallengingUser = challengeRepository.findAllByStatusAndChallengingUser(ChallengeStatus.COMPLETED, user);
        allCompleted.addAll(allByStatusAndChallengingUser);
        return allCompleted;
    }

    @Override
    public Challenge findById(Long id) {
        return challengeRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Challenge acceptChallenge(Long challengeId) {
        Challenge ch = challengeRepository.findById(challengeId).get();
        User challengingUser = ch.getChallengingUser();
        User challengedUser = ch.getChallengedUser();

        challengingUser.setPoints(challengingUser.getPoints() - ch.getPoints());
        challengedUser.setPoints(challengedUser.getPoints() - ch.getPoints());

        ch.setStatus(ChallengeStatus.ONGOING);
        Date now = new Date();
        ch.setStartTime(now);
        long time = now.getTime();
        time += (long) ch.getMinutes() * 60 * 1000;
        ch.setEndTime(new Date(time));
        return ch;
    }

    @Override
    @Transactional
    public User getWinner(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).get();
        Date startTime = challenge.getStartTime();
        Date endTime = challenge.getEndTime();

        User challengingUser = challenge.getChallengingUser();
        User challengedUser = challenge.getChallengedUser();

        List<ProblemSolution> solutions1 = problemSolutionRepository.findAllByUserAndSubmissionDateBetween(challengedUser, startTime, endTime);
        List<ProblemSolution> solutions2 = problemSolutionRepository.findAllByUserAndSubmissionDateBetween(challengingUser, startTime, endTime);

        int count1 = 0, count2 = 0;
        Problem problem = challenge.getProblem();

        for (ProblemSolution problemSolution : solutions1) {
            if(problem.equals(problemSolution.getProblem())) {
                if(problemSolution.getScore() > count1) {
                    count1 = problemSolution.getScore();
                }
            }
        }

        for (ProblemSolution problemSolution : solutions2) {
            if(problem.equals(problemSolution.getProblem())) {
                if(problemSolution.getScore() > count2) {
                    count2 = problemSolution.getScore();
                }
            }
        }

        int challengePoints = challenge.getPoints();

        if(count1 > count2) {
           challengedUser.setPoints(challengedUser.getPoints() + challengePoints * 2);
           return challengedUser;
       } else if(count1 < count2) {
           challengingUser.setPoints(challengingUser.getPoints() + challengePoints * 2);
           return challengingUser;
       } else {
           challengedUser.setPoints(challengedUser.getPoints() + challengePoints);
           challengingUser.setPoints(challengingUser.getPoints() + challengePoints);
           return null;
       }

    }



}
