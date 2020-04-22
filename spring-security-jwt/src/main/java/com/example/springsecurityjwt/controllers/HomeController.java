package com.example.springsecurityjwt.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springsecurityjwt.models.AuthenticationRequest;
import com.example.springsecurityjwt.models.AuthenticationResponse;
import com.example.springsecurityjwt.services.MyUserDetailsService;
import com.example.springsecurityjwt.util.JwtUtil;

@RestController
public class HomeController {
	
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);


	@Autowired
	private AuthenticationManager authenticationManager; // Created by a Bean at configuration for .authenticate() purpose

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MyUserDetailsService userDetailsService;

	@GetMapping("/hello")
	public String firstPage() {
		return "Hello World";
	}
	
//	 ---------This end-point takes the parameters for login via RequestBody (practice purpose).
//	AuthenticationRequest (Created in the models)
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			
			// ---- UsernamePasswordAuthenticationToken is spring's way to present user name and password as an authentication request object
			UsernamePasswordAuthenticationToken authenticationRequestObject =
									new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword());
			
			
			// ---- Uses overrode loadByUserName() of UserDetailsService to check UserDetails object
			Authentication authenticatedObject = authenticationManager.authenticate(authenticationRequestObject);  
			
			log.info("------- Authentication done for principal : " + authenticatedObject.getPrincipal().toString());

		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername()); // Getting UserDetais object
		final String jwt = jwtUtil.generateToken(userDetails);			// Creating a JWT(Json web token as String) using UserDetails
		return ResponseEntity.ok(new AuthenticationResponse(jwt));      // Carries an entity to the caller along with status in response to call.
	}
}