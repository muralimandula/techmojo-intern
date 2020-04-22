package com.example.mysql;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MysqlApplication {

	public static void main(String[] args) throws IOException {
		
		new RunHelper().killAndRun();
		
		SpringApplication.run(MysqlApplication.class, args);
	}

}
