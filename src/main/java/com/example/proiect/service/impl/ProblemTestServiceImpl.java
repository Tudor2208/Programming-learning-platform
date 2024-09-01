package com.example.proiect.service.impl;

import com.example.proiect.model.ProblemTest;
import com.example.proiect.model.User;
import com.example.proiect.repository.ProblemTestRepository;
import com.example.proiect.service.ProblemTestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemTestServiceImpl implements ProblemTestService {
    private final ProblemTestRepository problemTestRepo;

    public ProblemTestServiceImpl(ProblemTestRepository problemTestRepo) {
        this.problemTestRepo = problemTestRepo;
    }

    @Override
    public ProblemTest createProblemTest(ProblemTest test) {
        return problemTestRepo.save(test);
    }

    @Override
    public List<ProblemTest> findAllProblemTests() {
        return (List<ProblemTest>)problemTestRepo.findAll();
    }

    @Override
    public ProblemTest findProblemTestById(Long id) {
        return problemTestRepo.findById(id).get();
    }

    @Override
    public ProblemTest updateProblemTest(Long id, ProblemTest test) {
        ProblemTest problemTestToUpdate = problemTestRepo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("ProblemTest with ID %d doesn't exist", id)));
        problemTestToUpdate.setProblem(test.getProblem());
        problemTestToUpdate.setInput(test.getInput());
        problemTestToUpdate.setOutput(test.getOutput());
        problemTestRepo.save(problemTestToUpdate);
        return problemTestToUpdate;
    }

    @Override
    public ProblemTest deleteProblemTest(Long id) {
        ProblemTest test = problemTestRepo.findById(id).get();
        problemTestRepo.delete(test);
        return test;
    }
}
