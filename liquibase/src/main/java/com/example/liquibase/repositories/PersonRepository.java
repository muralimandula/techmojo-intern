package com.example.liquibase.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.liquibase.models.Person;

public interface PersonRepository extends CrudRepository<Person, Integer>{

	@Query("SELECT name FROM Person p WHERE p.name LIKE %:personName%")
	String findByName(String personName);
}
