package com.example.proiect.service;

import com.example.proiect.dto.AuthDTO;
import com.example.proiect.exceptions.ApiExceptionResponse;
import com.example.proiect.model.User;
import org.hibernate.service.spi.InjectService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    User createUser(User user) throws ApiExceptionResponse;
    List<User> findAllUsers();
    User findUserById(Long id);
    User updateUser(Long id, User user);
    User deleteUser(Long id);
    User findUserByUsername(String username);
    User findFirstByNameAndPassword(AuthDTO dto);

    int countUsersRegisteredToday();
    int countUsersRegisteredLastWeek();
    int countUsersRegisteredLastMonth();

}
