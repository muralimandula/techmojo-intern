package io.javabrains.moviecatalogservice;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import org.springframework.web.client.RestTemplate;

import io.javabrains.moviecatalogservice.services.FeignClientService;
import io.javabrains.moviecatalogservice.services.MovieInfoService;
import io.javabrains.moviecatalogservice.services.UserRatingsService;

@SpringBootApplication
@EnableFeignClients    // Helps accessing from feign clients
@EnableConfigServer     // Helps getting configuration/properties (.yml) from sources outside application(Ex: local github repo) and cater to other clients
@EnableEurekaClient     //Behaves a eureka client, that can use or get used by other eureka clients
@EnableCircuitBreaker   // Enables circuit breaker, can be used on a method that calls a service to make fault tolerant (set timeout, sleep, etc.,)
@EnableHystrixDashboard // Hystrix dash board, an end-point that shows statistics of service calls
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		/** 
		 * -------------------------------TO IMPLEMENT a simple timeout for response on a rest template used to call a service
		 * 
		HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		httpComponentsClientHttpRequestFactory.setConnectTimeout(2000); // 2000 milliseconds, 2 seconds
		return new RestTemplate(httpComponentsClientHttpRequestFactory);
		 */

		
		return new RestTemplate();
	}
	
	@Bean
	public MovieInfoService getMovieInfoService() {
		return new MovieInfoService();
	}
	
	@Bean 
	public UserRatingsService getUserRatiungsService() {
		return new UserRatingsService();
	}
	
	@Bean 
	public FeignClientService getFeignClientService( ) {
		return new FeignClientService();
	}
	
	@Bean
	public DiscoveryClient getDiscoveryClient() {
		return new DiscoveryClient() {
			
			@Override
			public List<String> getServices() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public List<ServiceInstance> getInstances(String serviceId) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String description() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
}

