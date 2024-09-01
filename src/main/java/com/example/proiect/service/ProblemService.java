package com.example.proiect.service;

import com.example.proiect.model.Problem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProblemService {
    Problem createProblem(Problem problem);
    List<Problem> findAllProblems();
    Problem findProblemById(Long id);
    Problem updateProblem(Long id, Problem problem);
    Problem deleteProblem(Long id);
    List<Problem> findAllPendingProblems();
    List<Problem> findAllApprovedProblems();

    Problem approveProblem(Long problemId, Long respAdminId);
    Problem denyProblem(Long problemId, Long respAdminId);
}
