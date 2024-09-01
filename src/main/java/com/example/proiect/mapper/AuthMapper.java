package com.example.proiect.mapper;

import com.example.proiect.dto.AuthDTO;
import com.example.proiect.model.User;

public class AuthMapper {
    public static AuthDTO mapUserToAuthDTO(User user) {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setUsername(user.getUsername());
        authDTO.setPassword(user.getPassword());
        return authDTO;
    }
}
