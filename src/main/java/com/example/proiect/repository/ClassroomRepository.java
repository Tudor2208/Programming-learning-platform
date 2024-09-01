package com.example.proiect.repository;

import com.example.proiect.model.Classroom;
import com.example.proiect.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomRepository extends CrudRepository<Classroom, Long> {
    List<Classroom> findAllByOwnerId(Long id);
    List<Classroom> findAllByStudentsContaining(User user);

}
