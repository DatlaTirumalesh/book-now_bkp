package com.journey_book_now.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.Cities;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.entity.Role;
import com.journey_book_now.service.JourneyService;


@RestController
@RequestMapping("/aero/travel")
public class JourneyController {
	
	@Autowired
	private JourneyService journeyService;	
	
	@GetMapping("/journeys")
	public ResponseEntity<List<Journey>> Journeys() {
		return ResponseEntity.ok(journeyService.findAll());
	}
	
	@PostMapping("/addJourney")
	public Journey addJourney(@RequestBody BusFromBusToDateDto addJourneyDto) {
		System.out.println(addJourneyDto);
		return journeyService.addJourney(addJourneyDto);
	}
	
	@GetMapping("/cities")
	public ResponseEntity<Set<String>> availableCities() {
		return ResponseEntity.ok(journeyService.findCities());
	}

}
