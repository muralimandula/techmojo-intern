package com.example.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.springbootsecurity.repositories.MyCustomUserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = MyCustomUserRepository.class)
public class SpringBootSecurityApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityApplication.class, args);
	}

}
