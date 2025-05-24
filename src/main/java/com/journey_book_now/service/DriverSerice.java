package com.journey_book_now.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.journey_book_now.entity.Driver;

@Service
public interface DriverSerice {

	List<Driver> findAll();

	Driver addDriver(Driver driver);
}
