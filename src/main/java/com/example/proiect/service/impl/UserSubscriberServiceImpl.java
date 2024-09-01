package com.example.proiect.service.impl;

import com.example.proiect.model.User;
import com.example.proiect.model.UserSubscriber;
import com.example.proiect.repository.UserSubscriberRepository;
import com.example.proiect.service.UserSubscriberService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriberServiceImpl implements UserSubscriberService {
    private final UserSubscriberRepository repo;

    public UserSubscriberServiceImpl(UserSubscriberRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserSubscriber createUserSubscriber(UserSubscriber userSubscriber) {
        return repo.save(userSubscriber);
    }

    @Override
    public List<UserSubscriber> findAllUsersSubscribers() {
        return (List<UserSubscriber>) repo.findAll();
    }

    @Override
    public UserSubscriber findUserSubscriberById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public UserSubscriber updateUserSubscriber(Long id, UserSubscriber user) {
        return null;
    }

    @Override
    public UserSubscriber deleteUserSubscriber(Long id) {
        UserSubscriber user = repo.findById(id).get();
        repo.delete(user);
        return user;
    }

    @Override
    public boolean existsUserSubscriber(User user) {
        return repo.existsUserSubscriberByUser(user);
    }

    @Override
    public Long findSubscriberByUserId(Long id) {
        return repo.findByUserId(id).getId();
    }
}
