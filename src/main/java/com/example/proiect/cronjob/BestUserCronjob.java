package com.example.proiect.cronjob;

import com.example.proiect.model.Notification;
import com.example.proiect.model.User;
import com.example.proiect.model.UserSubscriber;
import com.example.proiect.service.NotificationService;
import com.example.proiect.service.ProblemSolutionService;
import com.example.proiect.service.impl.EmailService;
import com.example.proiect.service.impl.UserSubscriberServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BestUserCronjob {
    private final ProblemSolutionService problemSolutionService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final UserSubscriberServiceImpl subscriberService;

    public BestUserCronjob(ProblemSolutionService problemSolutionService, EmailService emailService, NotificationService notificationService, UserSubscriberServiceImpl subscriberService) {
        this.problemSolutionService = problemSolutionService;
        this.emailService = emailService;
        this.notificationService = notificationService;

        this.subscriberService = subscriberService;
    }

    //1h
    @Scheduled(fixedDelay = 3600000, initialDelay = 1000)
    public void sendEmails() {
        User userWithMostSolutionToday = problemSolutionService.findUserWithTheMostSolutionsToday();
        List<UserSubscriber> allUsersSubscribers = subscriberService.findAllUsersSubscribers();
        for (UserSubscriber subscriber : allUsersSubscribers) {

            Notification notification = new Notification();
            if(userWithMostSolutionToday != null) {
                notification.setText("The most active user today was: " + userWithMostSolutionToday.getUsername() + ". Congratulations!");
            }
            else {
                notification.setText("Unfortunately, no user has solved at least one problem today!");
            }
            notification.setSendingDate(new Date());
            notification.setUser(subscriber.getUser());
            notificationService.createNotification(notification);

            String to = subscriber.getUser().getEmail();
            String subject = "Coding Problems Platform - MOST active user today!";
            String body = notification.getText();
           // emailService.sendEmail(to, subject, body);
           // System.out.println("Daily email was sent");
        }
    }
}
