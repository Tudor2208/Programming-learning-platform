package com.example.proiect.service.impl;

import com.example.proiect.model.User;
import com.example.proiect.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceImplTest {
    private UserServiceImpl userService;
    private static final String USERNAME = "TUDOR";
    private static final String USERNAME_NOT = "ANDREI";

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        initMocks(this);
        user = new User();
        user.setUsername(USERNAME);
        user.setId(0L);
        when(userRepository.findByUsername(USERNAME)).thenReturn(user);
        when(userRepository.findById(0L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findById(user.getId())).thenReturn(Optional.ofNullable(user));
    }


    @Test
    void givenExistingUsername_whenFindByUsername_thenFindOne() {
        //given
        userService = new UserServiceImpl(userRepository, passwordEncoder);

        //when
        User myUser = userService.findUserByUsername(USERNAME);

        //then
        assertNotNull(myUser);
        assertEquals(USERNAME, user.getUsername());
    }

    @Test
    void givenNonExistingUsername_whenFindByUsername_thenThrowException() {
        when(userRepository.findByUsername(USERNAME_NOT)).thenReturn(null);

        assertThrows(NullPointerException.class, () -> {
            userService.findUserByUsername(USERNAME_NOT);
        });
    }

    @Test
    void givenExistingUsername_whenDeleteByUsername_thenUserIsDeleted() {
        //given
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        Long userId = user.getId();

        assertNotNull(userService.findUserByUsername(USERNAME));

        when(userRepository.existsById(userId)).thenReturn(true);
        doNothing().when(userRepository).deleteById(userId);

    }

    @Test
    void givenValidUser_whenCreateUser_thenUserIsCreated() {
        //given
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        User newUser = new User();
        newUser.setUsername("Ion");

        when(userRepository.save(newUser)).thenReturn(newUser);

        userService.createUser(newUser);

        verify(userRepository, times(1)).save(newUser);
    }





}