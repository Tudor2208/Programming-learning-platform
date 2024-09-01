package com.example.proiect.controllers;

import com.example.proiect.model.ProblemSolution;
import com.example.proiect.model.ProblemTest;
import com.example.proiect.service.impl.ProblemSolutionServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/solution")
public class ProblemSolutionController {

    private final ProblemSolutionServiceImpl service;

    public ProblemSolutionController(ProblemSolutionServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllSolutions() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllProblemSolutions());
    }

    @GetMapping("/{id}")
    public ResponseEntity findSolutionById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProblemSolutionById(id));
    }

    @PostMapping
    public ResponseEntity saveNewSolution(@RequestBody ProblemSolution solution){
        return ResponseEntity.status(HttpStatus.OK).body(service.createProblemSolution(solution));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateSolution(@PathVariable Long id, @RequestBody ProblemSolution solution) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateProblemSolution(id, solution));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSolution(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteProblemSolution(id));
    }
    @PostMapping("/evaluate/{problemID}/{userID}")
    public ResponseEntity checkSolution(@PathVariable Long problemID, @PathVariable Long userID, @RequestBody String code) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(service.getScore(code, problemID, userID));
    }

    @GetMapping("/maxscore/{problemID}/{userID}")
    public ResponseEntity getUserSolutions(@PathVariable Long problemID, @PathVariable Long userID){
        return ResponseEntity.status(HttpStatus.OK).body(service.findMaxScoreByProblemAndUser(problemID, userID));
    }

}
