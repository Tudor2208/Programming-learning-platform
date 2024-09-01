package com.example.proiect.service.impl;

import com.example.proiect.dto.AuthDTO;
import com.example.proiect.exceptions.ApiExceptionResponse;
import com.example.proiect.model.User;
import com.example.proiect.repository.UserRepository;
import com.example.proiect.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(User user) throws ApiExceptionResponse {
        if(userRepo.findByUsername(user.getUsername()) == null) {
            Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if(!violations.isEmpty()) {
                StringBuilder msg = new StringBuilder();
                int ct = 0;
                for (ConstraintViolation<User> violation : violations) {
                    ct ++;
                    msg.append(ct).append(") ").append(violation.getMessage()).append("\n");
                }

                throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message(msg.toString()).errors(Collections.singletonList("error")).build();
            }
            user.setRegisterDate(new Date());
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            user.setPoints(100);
            return userRepo.save(user);
        } else {
            throw ApiExceptionResponse.builder().status(HttpStatus.NOT_FOUND).message("This username already exists!").errors(Collections.singletonList("error")).build();
        }

    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>)userRepo.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public User updateUser(Long id, User user) {
        User userToUpdate = userRepo.findById(id).orElseThrow(()-> new IllegalStateException(String.format("User with ID %d doesn't exist", id)));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        userToUpdate.setClassrooms(user.getClassrooms());
        userToUpdate.setRole(user.getRole());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setPoints(user.getPoints());
        userRepo.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public User deleteUser(Long id) {
        User user = userRepo.findById(id).get();
        userRepo.delete(user);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public User findFirstByNameAndPassword(AuthDTO dto) {
        return userRepo.findFirstByUsernameAndPassword(dto.getUsername(), dto.getPassword());
    }

    @Override
    public int countUsersRegisteredToday() {
        Date today = getStartOfDay(new Date());
        return userRepo.countAllByRegisterDateAfter(today);
    }

    @Override
    public int countUsersRegisteredLastWeek() {
        Date oneWeekAgo = getStartOfDay(getOffsetDate(-7));
        return userRepo.countAllByRegisterDateAfter(oneWeekAgo);
    }

    @Override
    public int countUsersRegisteredLastMonth() {
        Date oneMonthAgo = getStartOfDay(getOffsetDate(-30));
        return userRepo.countAllByRegisterDateAfter(oneMonthAgo);
    }

    private Date getOffsetDate(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
