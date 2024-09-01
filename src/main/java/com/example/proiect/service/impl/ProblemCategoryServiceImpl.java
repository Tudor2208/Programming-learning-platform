package com.example.proiect.service.impl;

import com.example.proiect.model.Problem;
import com.example.proiect.model.ProblemCategory;
import com.example.proiect.repository.ProblemCategoryRepository;
import com.example.proiect.service.ProblemCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemCategoryServiceImpl implements ProblemCategoryService {
    private final ProblemCategoryRepository repo;

    public ProblemCategoryServiceImpl(ProblemCategoryRepository repo) {
        this.repo = repo;
    }

    @Override
    public ProblemCategory createCategory(ProblemCategory category) {
        return repo.save(category);
    }

    @Override
    public List<ProblemCategory> findAllCategories() {
        return (List<ProblemCategory>)repo.findAll();
    }

    @Override
    public ProblemCategory findCategoryById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public ProblemCategory updateCategory(Long id, ProblemCategory category) {
        ProblemCategory categoryToUpdate = repo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("Category with ID %d doesn't exist", id)));
        categoryToUpdate.setProblems(category.getProblems());
        categoryToUpdate.setGrade(category.getGrade());
        categoryToUpdate.setName(category.getName());
        repo.save(categoryToUpdate);
        return categoryToUpdate;
    }

    @Override
    public ProblemCategory deleteCategory(Long id) {
        ProblemCategory category = repo.findById(id).get();
        repo.delete(category);
        return category;
    }
}
