package com.example.proiect.controllers;

import com.example.proiect.model.ProblemCategory;
import com.example.proiect.service.impl.ProblemCategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/category")
public class ProblemCategoryController {

    private final ProblemCategoryServiceImpl service;

    public ProblemCategoryController(ProblemCategoryServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity findCategoryById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findCategoryById(id));
    }

    @PostMapping
    public ResponseEntity saveNewCategory(@RequestBody ProblemCategory category){
        return ResponseEntity.status(HttpStatus.OK).body(service.createCategory(category));
    }
    @PutMapping("/{id}")
    public ResponseEntity updateCateogry(@PathVariable Long id, @RequestBody ProblemCategory category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteCategory(id));
    }

}
