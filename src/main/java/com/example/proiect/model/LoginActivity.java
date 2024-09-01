package com.example.proiect.model;

import com.example.proiect.model.enums.ActivityType;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Builder
@ToString
public class LoginActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private ActivityType activityType;

}
