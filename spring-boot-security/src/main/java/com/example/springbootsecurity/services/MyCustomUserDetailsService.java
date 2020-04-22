package com.example.springbootsecurity.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.springbootsecurity.models.MyCustomUserDetails;
import com.example.springbootsecurity.models.User;
import com.example.springbootsecurity.repositories.MyCustomUserRepository;


@Service
public class MyCustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	MyCustomUserRepository userRepository;
	

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        
        
        System.out.println("----USER FOUND : " + user.toString());
        
//        return new MyCustomUserDetails(user.get());      // Creating an UserDetails object, using User info retrieved from the Database.
        
        return user.map(MyCustomUserDetails::new).get();
    }
}
