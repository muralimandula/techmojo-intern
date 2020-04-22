package com.example.feignClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient   //8082
public class SpringCloudFeignApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(SpringCloudFeignApplication.class, args);
	}
	
	
	


}
