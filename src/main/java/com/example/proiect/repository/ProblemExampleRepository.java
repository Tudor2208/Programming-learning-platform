package com.example.proiect.repository;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemExample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemExampleRepository extends CrudRepository<ProblemExample, Long> {
    List<ProblemExample> findAllByProblem(Problem problem);
}
