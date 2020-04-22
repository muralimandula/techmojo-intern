package com.example.feignClient.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.feignClient.clients.UserClient;
import com.example.feignClient.models.FeignResponse;

@RestController
public class MainController {

	
	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private UserClient feignUserClient;
	
	@GetMapping("")
	public String landing() {
		log.info("------------------Checing");
		return "Checking---------------------";
	}
	
	@GetMapping("/alluser")
	public List<FeignResponse> getFeignResponses() {
		return feignUserClient.getAllUsers();
	}
}
