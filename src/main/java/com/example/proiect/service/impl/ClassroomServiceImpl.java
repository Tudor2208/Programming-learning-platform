package com.example.proiect.service.impl;

import com.example.proiect.model.Classroom;
import com.example.proiect.model.User;
import com.example.proiect.repository.ClassroomRepository;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.ClassroomService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;


@Service
public class ClassroomServiceImpl implements ClassroomService {
   private final ClassroomRepository repo;
   private final UserRepository userRepo;

    public ClassroomServiceImpl(ClassroomRepository repo, UserRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    public Classroom createClassroom(Classroom classroom) {
        return repo.save(classroom);
    }

    @Override
    public List<Classroom> findAllClassrooms() {
        return (List<Classroom>) repo.findAll();
    }

    @Override
    public Classroom findClassroomById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public Classroom updateClassroom(Long id, Classroom classroom) {
        Classroom classroomToUpdate = repo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("Classroom with ID %d doesn't exist", id)));
        classroomToUpdate.setName(classroom.getName());
        classroomToUpdate.setPassword(classroom.getPassword());
        classroomToUpdate.setOwner(classroom.getOwner());
        classroomToUpdate.setStudents(classroom.getStudents());
        classroomToUpdate.setDescription(classroom.getDescription());
        return repo.save(classroomToUpdate);
    }

    @Override
    public Classroom deleteClassroom(Long id) {
      Classroom classroom = repo.findById(id).get();
      repo.delete(classroom);
      return classroom;
    }

    @Override
    public List<Classroom> findAllByOwnerId(Long id) {
        return repo.findAllByOwnerId(id);
    }

    @Override
    @Transactional
    public void addStudentToClassroom(Long classroomId, Long studentId) {
        Classroom classroom = repo.findById(classroomId).get();
        User user = userRepo.findById(studentId).get();
        Set<User> students = classroom.getStudents();
        user.getClassrooms().add(classroom);
        students.add(user);
    }

    @Override
    public List<Classroom> findAllStudentsClassrooms(Long id) {
        User user = userRepo.findById(id).get();
        return repo.findAllByStudentsContaining(user);
    }

    @Override
    public Set<User> getClassroomMembers(Long classroomId) {
        Classroom classroom = repo.findById(classroomId).get();
        Set<User> members = classroom.getStudents();
        User owner = classroom.getOwner();
        members.add(owner);
        return members;
    }

    @Override
    @Transactional
    public User deleteStudentFromClassroom(Long classroomId, Long studentId) {
        User user = userRepo.findById(studentId).get();
        Classroom classroom = repo.findById(classroomId).get();
        Set<User> students = classroom.getStudents();
        students.remove(user);
        return user;
    }


}
