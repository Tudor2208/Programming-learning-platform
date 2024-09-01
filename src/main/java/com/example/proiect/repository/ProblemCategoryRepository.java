package com.example.proiect.repository;

import com.example.proiect.model.ProblemCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemCategoryRepository extends CrudRepository<ProblemCategory, Long> {
}
