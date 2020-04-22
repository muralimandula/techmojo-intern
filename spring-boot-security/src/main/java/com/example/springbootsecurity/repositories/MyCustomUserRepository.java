package com.example.springbootsecurity.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springbootsecurity.models.User;

public interface MyCustomUserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByUserName(String userName);
}
