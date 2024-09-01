package com.example.proiect.controllers;

import com.example.proiect.model.Classroom;
import com.example.proiect.model.ProblemExample;
import com.example.proiect.service.impl.ProblemExampleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/example")
public class ProblemExampleController {

    private final ProblemExampleServiceImpl service;

    public ProblemExampleController(ProblemExampleServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllExamples() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllProblemExamples());
    }

    @GetMapping("/{id}")
    public ResponseEntity findExampleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProblemExampleById(id));
    }

    @PostMapping
    public ResponseEntity saveNewExample(@RequestBody ProblemExample example){
        return ResponseEntity.status(HttpStatus.OK).body(service.createProblemExample(example));
    }
    @PutMapping("/{id}")
    public ResponseEntity updateExample(@PathVariable Long id, @RequestBody ProblemExample example) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProblemExample(id, example));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteExample(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProblemExample(id));
    }

    @GetMapping("/problem/{id}")
    public ResponseEntity findProblemExamples(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByProblem(id));
    }
}
