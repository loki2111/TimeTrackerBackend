package com.example.timetracker.service;

import com.example.timetracker.entity.User;
import com.example.timetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(user.getPassword());
        return userRepository.save(user);
    }
    public User fetchUserByEmailAndPassword(String email, String password)
    {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }


    public User savelogouttime(User user) {
        user.setLogouttime(user.getLogouttime());
        return userRepository.save(user);
    }
}
