package com.example.proiect.controllers;

import com.example.proiect.model.Classroom;
import com.example.proiect.service.impl.ClassroomServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classroom")
@CrossOrigin
public class ClassroomController {
    private final ClassroomServiceImpl service;


    public ClassroomController(ClassroomServiceImpl service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity findAllClassrooms() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllClassrooms());
    }

    @GetMapping("/{id}")
    public ResponseEntity findClassroomById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findClassroomById(id));
    }

    @PostMapping
    public ResponseEntity saveNewClassroom(@RequestBody Classroom classroom){
        return ResponseEntity.status(HttpStatus.OK).body(service.createClassroom(classroom));
    }
    @PutMapping("/{id}")
    public ResponseEntity updateClassroom(@PathVariable Long id, @RequestBody Classroom classroom) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateClassroom(id, classroom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClassroom(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteClassroom(id));
    }
    @GetMapping("/owner/{id}")
    public ResponseEntity findClassroomsByOwnerId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByOwnerId(id));
    }

    @PostMapping("/add-student/{classroomId}/{studentId}")
    public ResponseEntity addStudentToClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        service.addStudentToClassroom(classroomId, studentId);
        return ResponseEntity.status(HttpStatus.OK).body("Student added");
    }

    @GetMapping("/student-id/{id}")
    public ResponseEntity findAllByStudentsContaining(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllStudentsClassrooms(id));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity findClassroomMembers(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getClassroomMembers(id));
    }

    @DeleteMapping("/remove-student/{classroomId}/{studentId}")
    public ResponseEntity removeStudentFromClassroom(@PathVariable Long classroomId, @PathVariable Long studentId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteStudentFromClassroom(classroomId, studentId));
    }
}
