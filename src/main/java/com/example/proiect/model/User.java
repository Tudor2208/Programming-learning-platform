package com.example.proiect.model;

import com.example.proiect.model.enums.Role;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    @Pattern(regexp = "^[A-Z][a-z]*$",
            message = "First name should start with an uppercase letter and then lowercase letters!")
    private String firstName;

    @Pattern(regexp = "^[A-Z][a-z]*$",
            message = "Last name should start with an uppercase letter and then lowercase letters!")
    private String lastName;

//    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
//            message = "Password should contain at least one lowercase letter, uppercase letter, special character and digit!")
    private String password;


    @Email(message="Incorrect email address!")
    private String email;

    private Date registerDate;

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Classroom> classrooms;

    private int points;

}
