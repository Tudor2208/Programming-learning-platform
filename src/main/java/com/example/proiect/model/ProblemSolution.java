package com.example.proiect.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class ProblemSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date submissionDate;
    private int score;

    @Column(length = 2000)
    private String code;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Problem problem;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private User user;

    public ProblemSolution(Date submissionDate, int score, String code, Problem problem, User user) {
        this.submissionDate = submissionDate;
        this.score = score;
        this.code = code;
        this.problem = problem;
        this.user = user;
    }
}
