package com.example.proiect.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
@Getter
@Setter
@Entity
@AllArgsConstructor
public class UserSubscriber {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private User user;

    private Date subscribtionDate;
    public UserSubscriber(User user) {
        this.user = user;
    }

    public UserSubscriber() {

    }
}