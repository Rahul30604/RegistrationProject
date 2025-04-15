package com.service2.consumer.JpaRepository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.service2.consumer.Entity.User;


public interface UserRepository extends JpaRepository<User, String> {
}
