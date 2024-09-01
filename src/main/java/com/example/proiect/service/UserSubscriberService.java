package com.example.proiect.service;

import com.example.proiect.dto.AuthDTO;
import com.example.proiect.model.User;
import com.example.proiect.model.UserSubscriber;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserSubscriberService {
    UserSubscriber createUserSubscriber(UserSubscriber userSubscriber);
    List<UserSubscriber> findAllUsersSubscribers();
    UserSubscriber findUserSubscriberById(Long id);
    UserSubscriber updateUserSubscriber(Long id, UserSubscriber user);
    UserSubscriber deleteUserSubscriber(Long id);

    boolean existsUserSubscriber(User user);
    Long findSubscriberByUserId(Long id);

}
