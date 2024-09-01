package com.example.proiect.model;

import com.example.proiect.model.enums.ChallengeStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class Challenge {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User challengingUser;

    @ManyToOne
    private User challengedUser;

    private int points;
    private int minutes;

    private Date createTime;
    private Date startTime;
    private Date endTime;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private ChallengeStatus status;

    @ManyToOne
    private Problem problem;

    @ManyToOne
    private User winner;
}
