package com.example.proiect.model;

import com.example.proiect.service.UserService;
import lombok.*;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "report")


public class Report {

    @XmlAttribute(name="current-date")
    private Date currentDate;

    @XmlElement(name="nr-of-users")
    private int nrOfUsers;

    @XmlElement(name="nr-users-registered-today")
    private int nrOfUsersRegisteredToday;

    @XmlElement(name="nr-users-registered-last-week")
    private int nrOfUsersRegisteredLastWeek;

    @XmlElement(name="nr-users-registered-last-month")
    private int nrOfStudentsRegisteredLastMonth;

    @XmlElement(name="nr-connected-users")
    private int nrUsersConnected;

    @XmlElement(name="most-active-user-today")
    private User mostActiveUserToday;


}

