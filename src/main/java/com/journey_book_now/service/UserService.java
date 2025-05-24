package com.journey_book_now.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.journey_book_now.Dto.EmailPojo;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.entity.User;

@Service
public interface UserService {

	List<User> findAll();
	User userTicketBooking(User user);
	@PreAuthorize("hasRole('ADMIN')")
	Map<String, Object> userTicketDetails(Long id);
	EmailPojo userDetailsWithTicketNum(Long id);
}
