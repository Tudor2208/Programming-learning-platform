package com.example.proiect.model;

import com.example.proiect.model.enums.Difficulty;
import com.example.proiect.model.enums.ProblemStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Observable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String problemStatement;

    @Column(length = 500)
    private String input;

    @Column(length = 500)
    private String output;

    private String restrictions;

    @Enumerated(EnumType.STRING)
    @Column(length = 15)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ProblemStatus status;

    @ManyToOne
    private User author;

    @ManyToOne
    private User respAdmin;

    @ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.MERGE)
    private Set<ProblemCategory> categories;

    @ManyToMany(fetch= FetchType.LAZY, cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Homework> homeworks;

    public Problem(Long id) {
        this.id = id;
    }

    public Problem(String id) {
        this.id = Long.parseLong(id);
    }
}
