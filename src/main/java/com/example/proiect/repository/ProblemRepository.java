package com.example.proiect.repository;

import com.example.proiect.model.Problem;
import com.example.proiect.model.enums.ProblemStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Long> {
    List<Problem> findAllByStatus(ProblemStatus status);
}
