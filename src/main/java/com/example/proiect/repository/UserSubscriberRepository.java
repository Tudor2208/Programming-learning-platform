package com.example.proiect.repository;

import com.example.proiect.model.User;
import com.example.proiect.model.UserSubscriber;
import org.springframework.data.repository.CrudRepository;

public interface UserSubscriberRepository extends CrudRepository<UserSubscriber, Long> {
    boolean existsUserSubscriberByUser(User user);
    UserSubscriber findByUserId(Long userId);
}
