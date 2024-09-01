package com.example.proiect.repository;

import com.example.proiect.model.ProblemTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemTestRepository extends CrudRepository<ProblemTest, Long> {

}
