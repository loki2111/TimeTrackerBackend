package com.example.timetracker.repository;

import com.example.timetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
    List<User> findAll();
}
