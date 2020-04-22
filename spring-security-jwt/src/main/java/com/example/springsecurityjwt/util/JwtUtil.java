package com.example.springsecurityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtUtil {

    private String SECRET_KEY = "secret";
    
    // -----------------------------GENERATES JWT TOKEN using UserDetails Object
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();			// AN empty Map to assign claims/Info
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
        		.setClaims(claims)
        			.setSubject(subject)
        			.setIssuedAt(new Date(System.currentTimeMillis()))
        			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
        			.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    
    
    // --------------------------------------------GENERATES JWT TOKEN using UserDetails Object

    public String extractUsername(String token) {
    	return extractAllClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
    	return extractAllClaims(token).getExpiration();
    }

    

    private Claims extractAllClaims(String token) {
   
    	JwtParser jwtParser = Jwts.parser();												  // Parser for Jwt token
    	Jws<Claims> claimsSet = jwtParser.setSigningKey(SECRET_KEY).parseClaimsJws(token);    // Parsing/Extracting claims/Info from token using secret-key
    	Claims claims = claimsSet.getBody();												  // Obtaining claims (A simple JSON kind))
    	
    	System.out.println("-------------" + claims.toString());
        return claims;
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}