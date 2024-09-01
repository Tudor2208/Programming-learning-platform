package com.example.proiect.repository;

import com.example.proiect.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findFirstByUsernameAndPassword(String username, String password);
    int countByRegisterDateBetween(Date t1, Date t2);
    int countAllByRegisterDateAfter(Date date);
}
