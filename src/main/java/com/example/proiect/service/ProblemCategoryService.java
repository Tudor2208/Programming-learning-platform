package com.example.proiect.service;

import com.example.proiect.model.ProblemCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProblemCategoryService {
    ProblemCategory createCategory(ProblemCategory category);
    List<ProblemCategory> findAllCategories();
    ProblemCategory findCategoryById(Long id);
    ProblemCategory updateCategory(Long id, ProblemCategory category);
    ProblemCategory deleteCategory(Long id);
}
