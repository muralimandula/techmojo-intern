package io.javabrains.moviecatalogservice.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import io.javabrains.moviecatalogservice.models.CatalogItem;
import io.javabrains.moviecatalogservice.models.Movie;
import io.javabrains.moviecatalogservice.models.Rating;

public class MovieInfoService {
	
	
	private static final Logger log = LoggerFactory.getLogger(MovieInfoService.class);
	
    @Autowired
    private RestTemplate restTemplate;
    
	// -------------------------------------Using custom fallBack to return a CatalogItem object
    public CatalogItem getCatalogItemFallback(Rating rating) {
    	log.info("---------------Fallback while using service : movie-info-service");
    	
    	return new CatalogItem("No movie found", "Nothing ra bhi!!", 0);
    }
    
//    circuitBreaker.requestVolumeThreshold
//    circuitBreaker.sleepWindowInMilliseconds
//    circuitBreaker.errorThresholdPercentage
    @HystrixCommand(fallbackMethod = "getCatalogItemFallback",
		    		commandProperties = {
			    		@HystrixProperty(name ="execution.isolation.thread.timeoutInMilliseconds", value = "2000"), // Response time must be with in 2 seconds
			    		@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),				// Monitoring last 5 responses
			    		@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),   		// Threshold, Percentage of failures accepted
			    		@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")			// No requests accepted for 5 seconds if failure goes beyond threshold
		    		},
		    		threadPoolKey = "movieInfoPool", // Create a separate pool of threads
		    		threadPoolProperties = {
		    				@HystrixProperty(name = "coreSize", value = "20"),  // No.of concurrent threads possible
		    				@HystrixProperty(name = "maxQueueSize", value = "10") // Max requests accepted beyond pool size
		    		})
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
		
		log.info("---Microservice named \"movie-info-service\", working!  retrieved movie info successfully. ");
		return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
	}
}
