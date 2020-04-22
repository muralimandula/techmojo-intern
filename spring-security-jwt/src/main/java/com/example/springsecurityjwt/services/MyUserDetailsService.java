package com.example.springsecurityjwt.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

	// --- This service is to override a default method (loadUserByUsername()) called by spring's UserDetailsService
	//     for authentication on a given userName
	//	   returns UserDetails on successful authentication.
	
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//    	                userName, password, authorities/roles
        return new User("foo", "foo", new ArrayList<>());			// Creating an User For temporary usage returning as UserDetails. This assumes authentication DONE.
    }
}