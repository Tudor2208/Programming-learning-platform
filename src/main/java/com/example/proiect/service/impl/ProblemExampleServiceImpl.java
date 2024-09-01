package com.example.proiect.service.impl;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemExample;
import com.example.proiect.repository.ProblemExampleRepository;
import com.example.proiect.repository.ProblemRepository;
import com.example.proiect.service.ProblemExampleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemExampleServiceImpl implements ProblemExampleService {
    private final ProblemExampleRepository problemExampleRepo;
    private final ProblemRepository problemRepository;

    public ProblemExampleServiceImpl(ProblemExampleRepository problemExampleRepo, ProblemRepository problemRepository) {
        this.problemExampleRepo = problemExampleRepo;
        this.problemRepository = problemRepository;
    }

    @Override
    public ProblemExample createProblemExample(ProblemExample example) {
        return problemExampleRepo.save(example);
    }

    @Override
    public List<ProblemExample> findAllProblemExamples() {
        return (List<ProblemExample>)problemExampleRepo.findAll();
    }

    @Override
    public ProblemExample findProblemExampleById(Long id) {
        return problemExampleRepo.findById(id).get();
    }

    @Override
    public ProblemExample updateProblemExample(Long id, ProblemExample example) {
        ProblemExample problemExampleToUpdate = problemExampleRepo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("ProblemExample with ID %d doesn't exist", id)));
        problemExampleToUpdate.setProblem(example.getProblem());
        problemExampleToUpdate.setOutput(example.getOutput());
        problemExampleToUpdate.setInput(example.getInput());
        problemExampleToUpdate.setExplanation(example.getExplanation());
        problemExampleRepo.save(problemExampleToUpdate);
        return problemExampleToUpdate;
    }

    @Override
    public ProblemExample deleteProblemExample(Long id) {
       ProblemExample example = problemExampleRepo.findById(id).get();
       problemExampleRepo.delete(example);
       return example;
    }

    @Override
    public List<ProblemExample> findAllByProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId).get();
        return problemExampleRepo.findAllByProblem(problem);
    }
}
