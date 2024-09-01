package com.example.proiect.service;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemSolution;
import com.example.proiect.model.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface ProblemSolutionService {
    ProblemSolution createProblemSolution(ProblemSolution solution);
    List<ProblemSolution> findAllProblemSolutions();
    ProblemSolution findProblemSolutionById(Long id);
    ProblemSolution updateProblemSolution(Long id, ProblemSolution solution);
    ProblemSolution deleteProblemSolution(Long id);
    int getScore(String code, Long problemID, Long userID) throws IOException;
    int findMaxScoreByProblemAndUser(Long problemID, Long userID);

    User findUserWithTheMostSolutionsToday();
}
