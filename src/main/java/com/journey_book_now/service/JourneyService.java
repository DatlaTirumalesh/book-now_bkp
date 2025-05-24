package com.journey_book_now.service;

import java.util.List;
import java.util.Set;

import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.Cities;
import com.journey_book_now.entity.Journey;

public interface JourneyService {
	
	
	List<Journey> findAll();
	
	Journey addJourney(BusFromBusToDateDto addJourneyDto);
	Set<String> findCities();
}
