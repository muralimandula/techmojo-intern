package com.example.springbootsecurity.models;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class MyCustomUserDetails implements UserDetails{
	// -----------------This class implements UserDetails(Spring) and takes our User.java for authentication.
	
	
	// --- Below are the set of fields that every UserDetails must have.
	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	
	public MyCustomUserDetails() {
		// TODO Auto-generated constructor stub
	}
	
	
	//--------------------- Takes in user form the database(If found) and creates an UserDetails(Object) that can be handled for authentication by the spring application
	
	public MyCustomUserDetails(User user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.active = user.isActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
				                .map(SimpleGrantedAuthority::new)
				                .collect(Collectors.toList());
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.active;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	
}
