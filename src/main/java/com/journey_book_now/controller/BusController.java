package com.journey_book_now.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.Cities;
import com.journey_book_now.Dto.JourneyDto;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.service.BusService;
import com.journey_book_now.service.JourneyService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/aero/travel")
public class BusController {
	
	@Autowired
	private BusService busSerive;
	

	
	@GetMapping("/buses")
	public ResponseEntity<List<Bus>> busesList() {
		return ResponseEntity.ok(busSerive.busesList());
		}
	
//	@PostMapping("/addBus")
//	public Bus addBus(@RequestBody Bus bus) {
//		System.out.println("BUS NUM"+bus.getBus_num()+ " BUS STATUS"+ bus.getBus_status());
//		return busSerive.addBus(bus);
//		}
	
	@PostMapping("/addBus")
	public Bus addBus(@RequestParam  String bus_num, @RequestParam  String bus_type) {
		System.out.println(bus_num+" "+bus_type);
		return busSerive.addBus(bus_num, bus_type);
		}
	
	@PermitAll
	@PostMapping("/availableBuses")
	public ResponseEntity<List<JourneyDto>> availableBuses(@RequestBody BusFromBusToDateDto journey) {
		System.out.println(journey);
		return ResponseEntity.ok(busSerive.availableBuses(journey));
	}


	@PatchMapping("/updateBusStatus{id}")
	public ResponseEntity<Bus> updateBusStatus(@PathVariable Long id,@RequestBody Map<String, Object> bus) {
	    System.out.println(id+" "+bus);
	    return ResponseEntity.ok(busSerive.updateBusStatus(id, bus));
	}

	
	@DeleteMapping("/removeBus")
	public ResponseEntity<Bus> removeBus(@RequestParam String bus_num) {
		System.out.println(bus_num);
		return ResponseEntity.ok(busSerive.removeBus(bus_num));
	}
	
}
