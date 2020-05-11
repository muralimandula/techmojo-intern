package com.example.liquibase.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.liquibase.models.Person;
import com.example.liquibase.repositories.PersonRepository;

@RestController
public class MainController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@PostMapping("create")
	public String createPerson(@RequestParam String personName) {
		System.out.println("---checking " + personName);
		personRepository.save(new Person(personName, "5.6"));
		return personName + " saved successfully wiht id, ";
	}
	
	@GetMapping("allPersons")
	public List<Person> getAllPersons() {
		return (List<Person>) personRepository.findAll();
	}
}
