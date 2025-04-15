package com.service2.consumer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service2.consumer.Entity.User;
import com.service2.consumer.JpaRepository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KafkaConsumerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @KafkaListener(topics = "registration", groupId = "registration-group")
    public void consume(String message) {
        try {
            User user = objectMapper.readValue(message, User.class);
            String clientId = user.getId(); 

          //System.out.println("Received message for clientId: " + clientId);

            Optional<User> existingUser = userRepository.findById(clientId);
            if (existingUser.isEmpty()) {
                userRepository.save(user); 
                redisTemplate.opsForValue().set("request_count:" + clientId, "1");
              //System.out.println("New user inserted ");
            } else {
                redisTemplate.opsForValue().increment("request_count:" + clientId);
               //ystem.out.println("User already exists");
            }

        } catch (DataIntegrityViolationException dive) {
           //ystem.out.println("Duplicate entry or constraint violation: " + dive.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
