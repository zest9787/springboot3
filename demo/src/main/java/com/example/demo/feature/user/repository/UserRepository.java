package com.example.demo.feature.user.repository;

import com.example.demo.feature.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
