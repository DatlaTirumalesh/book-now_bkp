package com.journey_book_now.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.journey_book_now.entity.Driver;
import com.journey_book_now.service.DriverSerice;

@RestController
@RequestMapping("/aero/admin")
public class DriverController {
	
	@Autowired
	private DriverSerice driverService;
	
	@GetMapping("/drivers")
	public List<Driver> driversList() {
		return driverService.findAll();
	}

	@PostMapping("addDriver")
	public Driver addDriver(@RequestBody Driver driver) 
	{
		return driverService.addDriver(driver);
	}
}
