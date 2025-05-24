package com.journey_book_now.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.journey_book_now.Dto.EmailPojo;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.entity.User;
import com.journey_book_now.service.UserService;

@RestController
@RequestMapping("/aero/travel")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/userTicketList")
	public ResponseEntity<List<User>> bookTicket() {
		return ResponseEntity.ok(userService.findAll());
	}
	
	@PostMapping("/userTicketBooking")
	public User userTicketBooking(@RequestBody User user) {
		System.out.println(user);
		return userService.userTicketBooking(user);
	}
	

	@GetMapping("/userTicketDetails{id}")
	public EmailPojo userTicketDetails(@PathVariable Long id) {
		System.out.println("Hit endpoint"+" "+id);
	    return userService.userDetailsWithTicketNum(id);
	}

	
}
