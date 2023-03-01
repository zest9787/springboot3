package com.example.demo.feature.user.ui;

import com.example.demo.feature.user.domain.User;
import com.example.demo.feature.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    @GetMapping
    public Optional<User> getUser(Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }

    @PutMapping
    public User updateUser(@RequestParam Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

}
