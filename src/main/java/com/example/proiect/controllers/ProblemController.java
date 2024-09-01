package com.example.proiect.controllers;

import com.example.proiect.model.Problem;
import com.example.proiect.service.impl.ProblemServiceImpl;
import com.sun.jdi.event.ExceptionEvent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/problem")
public class ProblemController {

    private final ProblemServiceImpl service;


    public ProblemController(ProblemServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllProblems() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllProblems());
    }

    @GetMapping("/{id}")
    public ResponseEntity findProblemById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProblemById(id));
    }

    @PostMapping
    public ResponseEntity saveNewProblem(@RequestBody Problem problem){
        return ResponseEntity.status(HttpStatus.OK).body(service.createProblem(problem));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateProblem(@PathVariable Long id, @RequestBody Problem problem) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProblem(id, problem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProblem(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProblem(id));
    }

    @GetMapping("/pending")
    public ResponseEntity getPendingProblems() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllPendingProblems());
    }

    @GetMapping("/approved")
    public ResponseEntity getApprovedProblems() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllApprovedProblems());
    }

    @PostMapping("/approve/{problemId}/{userRespId}")
    public ResponseEntity approveProblem(@PathVariable Long problemId, @PathVariable Long userRespId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.approveProblem(problemId, userRespId));
    }

    @PostMapping("/deny/{problemId}/{userRespId}")
    public ResponseEntity denyProblem(@PathVariable Long problemId, @PathVariable Long userRespId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.denyProblem(problemId, userRespId));
    }
}
