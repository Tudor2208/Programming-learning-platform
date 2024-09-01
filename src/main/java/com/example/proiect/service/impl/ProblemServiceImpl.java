package com.example.proiect.service.impl;

import com.example.proiect.events.NewProblemEvent;
import com.example.proiect.model.Notification;
import com.example.proiect.model.Problem;
import com.example.proiect.model.User;
import com.example.proiect.model.enums.ProblemStatus;
import com.example.proiect.repository.ProblemRepository;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    private final ProblemRepository repo;

    private final UserRepository userRepository;

    public ProblemServiceImpl(ProblemRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
    }

    @Override
    public Problem createProblem(Problem problem) {
        applicationEventPublisher.publishEvent(new NewProblemEvent(this, problem));
        return repo.save(problem);
    }

    @Override
    public List<Problem> findAllProblems() {
        return (List<Problem>) repo.findAll();
    }

    @Override
    public Problem findProblemById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Problem updateProblem(Long id, Problem problem) {
        Problem problemToUpdate = repo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("Problem with ID %d doesn't exist", id)));
        problemToUpdate.setProblemStatement(problem.getProblemStatement());
        problemToUpdate.setInput(problem.getInput());
        problemToUpdate.setOutput(problem.getOutput());
        problemToUpdate.setAuthor(problem.getAuthor());
        problemToUpdate.setCategories(problem.getCategories());
        problemToUpdate.setDifficulty(problem.getDifficulty());
        problemToUpdate.setRespAdmin(problem.getRespAdmin());
        problemToUpdate.setName(problem.getName());
        problemToUpdate.setStatus(problem.getStatus());
        problemToUpdate.setRestrictions(problem.getRestrictions());
        return repo.save(problemToUpdate);
    }

    @Override
    public Problem deleteProblem(Long id) {
        Problem problem = repo.findById(id).get();
        repo.delete(problem);
        return problem;
    }

    @Override
    public List<Problem> findAllPendingProblems() {
        return repo.findAllByStatus(ProblemStatus.PENDING);
    }

    @Override
    public List<Problem> findAllApprovedProblems() {
        return repo.findAllByStatus(ProblemStatus.APPROVED);
    }

    @Override
    @Transactional
    public Problem approveProblem(Long problemId, Long respAdminId) {
        Problem problem = repo.findById(problemId).get();
        User user = userRepository.findById(respAdminId).get();

        problem.setRespAdmin(user);
        problem.setStatus(ProblemStatus.APPROVED);

        return problem;
    }

    @Override
    @Transactional
    public Problem denyProblem(Long problemId, Long respAdminId) {
        Problem problem = repo.findById(problemId).get();
        User user = userRepository.findById(respAdminId).get();
        problem.setRespAdmin(user);
        problem.setStatus(ProblemStatus.DENIED);
        return problem;
    }
}
