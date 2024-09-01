package com.example.proiect.events;

import com.example.proiect.model.Notification;
import com.example.proiect.model.Problem;
import com.example.proiect.model.UserSubscriber;
import com.example.proiect.service.NotificationService;
import com.example.proiect.service.impl.EmailService;
import com.example.proiect.service.impl.UserSubscriberServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NotificationHandler implements ApplicationListener<NewProblemEvent> {

    private final UserSubscriberServiceImpl subscriberService;
    private final NotificationService notificationService;

    private final EmailService emailService;

    public NotificationHandler(UserSubscriberServiceImpl subscriberService, NotificationService notificationService, EmailService emailService) {
        this.subscriberService = subscriberService;
        this.notificationService = notificationService;
        this.emailService = emailService;
    }


    @Override
    public void onApplicationEvent(NewProblemEvent event) {
        Problem problem = event.getProblem();
        List<UserSubscriber> allUsersSubscribers = subscriberService.findAllUsersSubscribers();
        for (UserSubscriber subscriber : allUsersSubscribers) {
            Notification notification = new Notification();
            notification.setText("A new problem has been added: " + problem.getName() + " (" + problem.getDifficulty() + ")");
            notification.setSendingDate(new Date());
            notification.setUser(subscriber.getUser());
            notificationService.createNotification(notification);

            String to = subscriber.getUser().getEmail();
            String subject = "Coding Problems Platform - NEW problem!";
            String body = notification.getText() + "\n" + "Problem statement: " + problem.getProblemStatement();
            emailService.sendEmail(to, subject, body);
        }


    }
}
