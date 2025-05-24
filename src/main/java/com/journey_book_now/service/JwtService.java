package com.journey_book_now.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
	@Autowired
    private final UserDetailsService userDetailsService;
	
	private final String SECRET_KEY = "abcbabbbasdbbasbdbasdbasdbasdbabsdabsdabsdbasd";
	private final long EXPIRATION_TIME  = 1000 * 60 * 15;
	
    public JwtService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}
	
	private Key getKey() {
		byte[] key = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(key);
	}
	
	private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.claim("roles", userDetails.getAuthorities())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME ))
				.signWith(getKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public String refreshToken(String token) {
		Map<String, Object> claims = extractAllClaims(token);
		System.out.println(claims+" "+"CLAIMS");
		String username = extractUsername(token);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME ))
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();

	}
	
	public boolean isTokenExpiringSoon(String token) {
		Date date = extractExpiration(token);
		long remaining = date.getTime() - System.currentTimeMillis();
		  logger.info("JWT Expiry Time Remaining: {} ms", remaining);
		return remaining < (EXPIRATION_TIME / 2);
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails ) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extractClaim(token,Claims::getExpiration);
				
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
			    .setSigningKey(getKey())
			    .setAllowedClockSkewSeconds(60)
			    .build()
			    .parseClaimsJws(token)
			    .getBody();
	}
}
