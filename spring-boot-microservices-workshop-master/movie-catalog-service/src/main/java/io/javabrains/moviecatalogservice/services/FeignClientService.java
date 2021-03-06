package io.javabrains.moviecatalogservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public class FeignClientService {
	
	@Autowired
	RestTemplate restTemplate;

	
	private static final Logger log = LoggerFactory.getLogger(FeignClientService.class);
	
	
	public String getFromFeign( ) {
		Object  fromFeignRestTemplate = restTemplate.getForEntity("http://feign-as-eureka-client/alluser", Object.class);
		log.info("---- Retrieved successfully form the feign client !!");
		return fromFeignRestTemplate.toString();
	}

}
