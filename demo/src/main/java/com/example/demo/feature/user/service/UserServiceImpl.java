package com.example.demo.feature.user.service;

import com.example.demo.feature.user.domain.User;
import com.example.demo.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    @Override
    public Optional<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User foundUser = userRepository.findById(id).get();
        foundUser.setName(user.getName());

        return userRepository.save(foundUser);
    }
}
