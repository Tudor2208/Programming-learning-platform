package com.example.proiect.service;

import com.example.proiect.model.Homework;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HomeworkService {
    Homework createHomework(Homework homework);
    List<Homework> findAllHomeworks();
    Homework findHomeworkById(Long id);
    List<Homework> findClassroomHomeworks(Long id);

    Homework deleteHomework (Long id);

    int probSolvedByAStudentFromHomework(Long userId, Long homeworkId);

    boolean expiredProblem(Long userId, Long problemId);
}
