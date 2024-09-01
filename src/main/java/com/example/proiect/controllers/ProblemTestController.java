package com.example.proiect.controllers;

import com.example.proiect.model.ProblemTest;
import com.example.proiect.service.impl.ProblemTestServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/test")
public class ProblemTestController {
    private final ProblemTestServiceImpl service;

    public ProblemTestController(ProblemTestServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllTests() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllProblemTests());
    }

    @GetMapping("/{id}")
    public ResponseEntity findTestById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProblemTestById(id));
    }

    @PostMapping
    public ResponseEntity saveNewTest(@RequestBody ProblemTest test){
        return ResponseEntity.status(HttpStatus.OK).body(service.createProblemTest(test));
    }
    @PutMapping("/{id}")
    public ResponseEntity updateTest(@PathVariable Long id, @RequestBody ProblemTest test) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProblemTest(id, test));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTest(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProblemTest(id));
    }
}
