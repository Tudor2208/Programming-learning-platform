package com.example.proiect.cronjob;

import com.example.proiect.model.Challenge;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ChallengeStatus;
import com.example.proiect.service.ChallengeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class EveryMinuteCronjob {
    private final ChallengeService challengeService;

    public EveryMinuteCronjob(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void checkPendingChallenges() {
        List<Challenge> allPendingChallenges = challengeService.findAllPendingChallenges();
        long currentTime = new Date().getTime();
        for (Challenge pendingChallenge : allPendingChallenges) {
            long time = pendingChallenge.getCreateTime().getTime();
            if(currentTime - time > 300000) {
                pendingChallenge.setStatus(ChallengeStatus.EXPIRED);
            }

        }

    }


    @Scheduled(fixedDelay = 60000)
    @Transactional
    public void checkOngoingChallenges() {
        List<Challenge> allOngoingChallenges = challengeService.findAllOngoingChallenges();
        for (Challenge ongoingChallenge : allOngoingChallenges) {
            Date endTime = ongoingChallenge.getEndTime();
            Date now = new Date();
            if(now.after(endTime)) {
                ongoingChallenge.setStatus(ChallengeStatus.COMPLETED);
                User winner = challengeService.getWinner(ongoingChallenge.getId());
                ongoingChallenge.setWinner(winner);
            }

        }

    }




}
