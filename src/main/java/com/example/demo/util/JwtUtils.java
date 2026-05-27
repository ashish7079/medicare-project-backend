package com.example.demo.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

	private final String secret = "mysecretkeymysecretkeymysecretkey";
	private final long Expiration_Time = 1000 * 60 * 60;
	
	private Key getSigningkey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
		
	}
		 public String generateToken(String emailId,String role){
			return Jwts.builder()
					.setSubject(emailId)
					.claim("role",role)
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + Expiration_Time))
	                .signWith(getSigningkey(), SignatureAlgorithm.HS256)
	                .compact();	
		
	}
		 public String extractemail(String token) {
			 return extractAllClaims(token).getSubject();
		 }
		public Date extractExpiration(String token) {
			return extractAllClaims(token).getExpiration();
		}
		public String extractRole(String token) {
			return extractAllClaims(token).get("role",String.class);
		}
		public boolean validateToken(String token,String emailId) {
			String extractemail = extractemail(token);
			return (extractemail.equals(emailId) && !isTokenExpired(token));
		}
		 
		private boolean isTokenExpired(String token) {
			return extractExpiration(token).before(new Date());

		}
		private Claims extractAllClaims(String token) {
			return Jwts.parserBuilder()
					.setSigningKey(getSigningkey())
					.build()
					.parseClaimsJws(token)
	                .getBody();
		}
}