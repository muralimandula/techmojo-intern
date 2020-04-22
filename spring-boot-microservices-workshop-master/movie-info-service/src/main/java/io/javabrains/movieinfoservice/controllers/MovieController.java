package io.javabrains.movieinfoservice.controllers;

import io.javabrains.movieinfoservice.AttributesProperties;
import io.javabrains.movieinfoservice.models.Movie;
import io.javabrains.movieinfoservice.models.MovieSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/movies")
@RefreshScope                  // Can refresh bean/properties/injections as done at application launch
public class MovieController {

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    AttributesProperties attributesProperties; // Using CongfigurationProperties, can retrieve fields in application.properties
    
    @GetMapping("/attributes")
    public String getAttributes() {
    	return "Profile : " + attributesProperties.getProfile() + "      User : "+ attributesProperties.getUser() + " on port :" + attributesProperties.getPort();
    }

    @RequestMapping("/{movieId}")
    public Movie getMovieInfo(@PathVariable("movieId") String movieId) {
//    														   https://api.themoviedb.org/3/movie/550?api_key=4d8d9caaa46821c1014996efd42f6dac
        MovieSummary movieSummary = restTemplate.getForObject("https://api.themoviedb.org/3/movie/" + movieId + "?api_key=" +  apiKey, MovieSummary.class);
        
        
        return new Movie(movieId, movieSummary.getTitle(), movieSummary.getOverview());

    }

}
