package com.example.proiect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String password;
    private String description;

    @ManyToOne
    private User owner;

    @ManyToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<User> students;
}
