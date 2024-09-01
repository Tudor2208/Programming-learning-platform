package com.example.proiect.repository;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemSolution;
import com.example.proiect.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProblemSolutionRepository extends CrudRepository<ProblemSolution, Long> {
    List<ProblemSolution> findAllByProblemAndUser(Problem problem, User user);
    List<ProblemSolution> findAllByUserAndSubmissionDateBetween(User user, Date start, Date end);
}
