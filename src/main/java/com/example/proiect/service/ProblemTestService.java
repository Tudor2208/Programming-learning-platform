package com.example.proiect.service;

import com.example.proiect.model.ProblemTest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProblemTestService {
    ProblemTest createProblemTest(ProblemTest test);
    List<ProblemTest> findAllProblemTests();
    ProblemTest findProblemTestById(Long id);
    ProblemTest updateProblemTest(Long id, ProblemTest test);
    ProblemTest deleteProblemTest(Long id);
}
