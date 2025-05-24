package com.journey_book_now.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journey_book_now.auth_pojo.AuthRegister;
import com.journey_book_now.auth_pojo.AuthRequest;
import com.journey_book_now.auth_pojo.AuthResponse;
import com.journey_book_now.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aero/travel")
@RequiredArgsConstructor
public class AuthenticationController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRegister register) {
		System.out.println(register);
		AuthResponse response = authService.regiter(register);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
		System.out.println(request);
		return ResponseEntity.ok(authService.authenticate(request));
	}
	
	@PostMapping("/refreshToken")
	public ResponseEntity<String> refreshToken(@RequestBody Map<String, String> payload) {
	    String token = payload.get("refreshToken");
	    System.out.println(token + " TOKEN"+" "+payload);
	    return ResponseEntity.ok(authService.refreshToken(token));
	}
}
