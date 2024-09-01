package com.example.proiect.service;

import com.example.proiect.model.Classroom;
import com.example.proiect.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ClassroomService {
    Classroom createClassroom(Classroom classroom);
    List<Classroom> findAllClassrooms();
    Classroom findClassroomById(Long id);
    Classroom updateClassroom(Long id, Classroom classroom);
    Classroom deleteClassroom(Long id);

    List<Classroom> findAllByOwnerId(Long id);

    void addStudentToClassroom(Long classroomId, Long studentId);
    List<Classroom> findAllStudentsClassrooms(Long id);

    Set<User> getClassroomMembers(Long classroomId);

    User deleteStudentFromClassroom(Long classroomId, Long studentId);
}
