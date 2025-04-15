package com.service2.consumer.controller;

import com.service2.consumer.model.User;
import com.service2.consumer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        
        return userRepository.findAll();
    }
}
