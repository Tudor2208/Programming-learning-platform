package com.example.proiect.repository;

import com.example.proiect.model.LoginActivity;
import com.example.proiect.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginActivityRepository extends CrudRepository<LoginActivity, Long> {
    List<LoginActivity> findAllByUser(User user);
}
