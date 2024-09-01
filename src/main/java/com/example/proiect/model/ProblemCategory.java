package com.example.proiect.model;

import com.example.proiect.model.enums.Grade;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ProblemCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Grade grade;

    private String name;
    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private Set<Problem> problems;
}
