package com.journey_book_now.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.journey_book_now.auth_pojo.AuthRegister;
import com.journey_book_now.auth_pojo.AuthRequest;
import com.journey_book_now.auth_pojo.AuthResponse;
import com.journey_book_now.entity.Auth;
import com.journey_book_now.entity.Role;
import com.journey_book_now.repo.AuthRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthRepo authRepo;

	private final JwtService jwt;

	private final AuthenticationManager authenticationManger;

	private final PasswordEncoder passwordEncoder;

	public AuthResponse regiter(AuthRegister register) {
		var existing = authRepo.findByEmail(register.getEmail());
		Auth auth = new Auth();
		if (existing.isEmpty()) {
			auth.setEmail(register.getEmail());
			auth.setFirstName(register.getFirstName());
			auth.setLastName(register.getLastName());
			auth.setPassword(passwordEncoder.encode(register.getPassword()));
			auth.setRole(Role.USER);
			System.out.println(auth);
			authRepo.save(auth);
		} else {
			throw new RuntimeException("User already present ");
		}
		var token = jwt.generateToken(auth);
		var refreshToken = jwt.refreshToken(token);
		return AuthResponse.builder().token(token).refreshToken(refreshToken).build();
	}

	public AuthResponse authenticate(AuthRequest request) {
		authenticationManger
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		var user = authRepo.findByEmail(request.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
		var token = jwt.generateToken(user);
		var refreshToken = jwt.refreshToken(token);
		return AuthResponse.builder().token(token).refreshToken(refreshToken).build();
	}

	public String refreshToken(String token) {
		var refreshToken = jwt.refreshToken(token);
		if(refreshToken.isEmpty()) {
			throw new RuntimeException("Refresh Token is Empty");
		}
		
		return refreshToken;
	}

}
