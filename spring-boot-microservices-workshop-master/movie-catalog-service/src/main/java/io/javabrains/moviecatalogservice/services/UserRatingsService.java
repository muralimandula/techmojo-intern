package io.javabrains.moviecatalogservice.services;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.javabrains.moviecatalogservice.models.Rating;
import io.javabrains.moviecatalogservice.models.UserRating;

public class UserRatingsService {
	
	private static final Logger log = LoggerFactory.getLogger(UserRatingsService.class);
	
	@Autowired
    private RestTemplate restTemplate;
	
	public UserRating getUserRatingsFallback(String userId) {
		
		log.info("--------------Fallback while using service : ratings-data-service");
		
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);  // Creating a dummy userRating, movieId : 0, rating : 0
        userRating.setRatings(Arrays.asList(
        		new Rating("0", 0)
        		));
        return userRating;
	}
	
	@HystrixCommand(fallbackMethod = "getUserRatingsFallback", 
    		commandProperties = {
    	    		@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "2000"), // Response time must be with in 2 seconds
    	    		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),				// Monitoring last 5 responses
    	    		@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),   		// Threshold, Percentage of failures accepted
    	    		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")			// No requests accepted for next 5 seconds if failure goes beyond threshold
        		},
        		threadPoolKey = "movieInfoPool", // Create a separate pool of threads
        		threadPoolProperties = {
        				@HystrixProperty(name = "coreSize", value = "20"),  // No.of concurrent threads possible
        				@HystrixProperty(name = "maxQueueSize", value = "10") // Max requests accepted beyond pool size
        		}
	)
	public UserRating getUserRatings(String userId) {
		UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
		log.info("---Microservice named \"ratings-data-service\", working!  retrieved list of movieId's successfully. ");
		
		return userRating;
	}
}
