package com.example.proiect.repository;

import com.example.proiect.model.Homework;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HomeworkRepository extends CrudRepository<Homework, Long> {
    List<Homework> findAllByClassroomId(Long id);
}
