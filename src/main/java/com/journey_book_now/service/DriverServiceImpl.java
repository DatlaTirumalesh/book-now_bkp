package com.journey_book_now.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journey_book_now.entity.Driver;
import com.journey_book_now.repo.DriverRepo;

@Service
public class DriverServiceImpl implements DriverSerice {

	@Autowired
	private DriverRepo driverRepo;

	@Override
	public List<Driver> findAll() {
		List<Driver> driverList = driverRepo.findAll();
		if (driverList.isEmpty()) {
			throw new RuntimeException("Drivers are list is Empty");
		}
		return driverList;
	}

	@Override
	public Driver addDriver(Driver driver) {
		Driver result = driverRepo.save(driver);
		return result;
	}

}
