package com.journey_book_now.configuration;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.journey_book_now.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserDetailsService userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;
	
		if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
	
			filterChain.doFilter(request, response);
			return;
		}
		jwt = authHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);
		if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
			if (jwtService.isTokenValid(jwt, userDetails)) {
				System.out.println("Token"+jwt+" "+jwtService.isTokenValid(jwt, userDetails));
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
			}
		       if (jwtService.isTokenExpiringSoon(jwt)) {
	                String refreshedToken = jwtService.refreshToken(jwt);
	                response.setHeader("Authorization", "Bearer " + refreshedToken);
	                System.out.println("Refresh Token "+refreshedToken);
	            }
		}
		filterChain.doFilter(request, response);
	}

}
