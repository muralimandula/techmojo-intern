package com.example.feignClient.clients;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.feignClient.models.FeignResponse;

@FeignClient(url = "https://jsonplaceholder.typicode.com", name = "FEIGN-USER-CLIENT")
public interface UserClient {
	
	@GetMapping("/users")
	List<FeignResponse> getAllUsers();
}
