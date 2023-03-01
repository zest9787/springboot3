package com.example.demo.feature.user.service;

import com.example.demo.feature.user.domain.User;

import java.util.Optional;

public interface UserService {

    Optional<User> getUser(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    User updateUser(Long id, User user);
}
