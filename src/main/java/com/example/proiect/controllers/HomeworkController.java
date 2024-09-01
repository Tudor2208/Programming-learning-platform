package com.example.proiect.controllers;

import com.example.proiect.model.Homework;
import com.example.proiect.model.Problem;
import com.example.proiect.service.HomeworkService;
import com.example.proiect.service.ProblemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/homework")
@CrossOrigin
public class HomeworkController {
    private final HomeworkService homeworkService;
    private final ProblemService problemService;

    public HomeworkController(HomeworkService homeworkService, ProblemService problemService) {
        this.homeworkService = homeworkService;
        this.problemService = problemService;
    }

    @PostMapping("/{problems}")
    public ResponseEntity createHomework(@RequestBody Homework homework, @PathVariable String[] problems){
        Set<Problem> problemSet = new HashSet<>();
        for (String problemStr : problems) {
            Problem problemById = problemService.findProblemById(Long.valueOf(problemStr));
            problemSet.add(problemById);
        }
        homework.setProblems(problemSet);
        return ResponseEntity.status(HttpStatus.OK).body(homeworkService.createHomework(homework));
    }


    @GetMapping("/classroom/{id}")
    public ResponseEntity getAllHome(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(homeworkService.findClassroomHomeworks(id));
    }

    @GetMapping("/nr-problems/{userId}/{homeworkId}")
    public ResponseEntity probSolvedByAStudentFromHomework(@PathVariable Long userId, @PathVariable Long homeworkId) {
        return ResponseEntity.status(HttpStatus.OK).body(homeworkService.probSolvedByAStudentFromHomework(userId, homeworkId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteHomework(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(homeworkService.deleteHomework(id));
    }

    @GetMapping("/check-expired/{userId}/{problemId}")
    public ResponseEntity checkExpiredProblem(@PathVariable Long userId, @PathVariable Long problemId) {
        return ResponseEntity.status(HttpStatus.OK).body(homeworkService.expiredProblem(userId, problemId));
    }

}
