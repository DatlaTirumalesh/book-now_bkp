package com.journey_book_now.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;

import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.JourneyDto;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.entity.Journey;

@Service
public interface BusService {

	List<Bus> busesList();
//	Bus addBus(Bus bus);
	Bus addBus(String bus_num, String bus_type);
	Bus updateBusStatus(Long id, Map<String, Object> data);
	List<JourneyDto> availableBuses(BusFromBusToDateDto journey);
	Bus removeBus(String bus_num);
}
