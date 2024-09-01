package com.example.proiect.service;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemExample;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProblemExampleService {
    ProblemExample createProblemExample(ProblemExample example);
    List<ProblemExample> findAllProblemExamples();
    ProblemExample findProblemExampleById(Long id);
    ProblemExample updateProblemExample(Long id, ProblemExample example);
    ProblemExample deleteProblemExample(Long id);

    List<ProblemExample> findAllByProblem(Long problemId);
}
