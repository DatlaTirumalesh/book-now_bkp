package com.journey_book_now.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.journey_book_now.entity.Driver;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.Cities;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.repo.BusRepo;
import com.journey_book_now.repo.DriverRepo;
import com.journey_book_now.repo.JourneyRepo;

@Service
public class JourneyServiceImpl implements JourneyService {

	@Autowired
	private JourneyRepo journeyRepo;

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Override
	public List<Journey> findAll() {
		return journeyRepo.findAll();
	}

//	@Override
//	public Journey addJourney(Journey journey) {
//		Journey newJourney = null;
//		Bus busId = busRepo.findById((long) journey.getBus_id()).orElseThrow(() -> new RuntimeException("Invalid Bus ID"));
//		Driver driverId = driverRepo.findById((long) journey.getDriver_id()).orElseThrow(() -> new RuntimeException("Invalid Driver ID"));
//		if ( busId.getBus_status() == 'Y' && driverId.getDriver_status() == 'Y') {
//			busId.setBus_status('N');
//			driverId.setDriver_status('N');
//			newJourney = journeyRepo.save(journey);
//			busRepo.save(busId);
//			driverRepo.save(driverId);
//		}
//		else {
//			throw new RuntimeException("Bus or Driver are not available");
//		}
//		return newJourney;
//	}

	@Override
	public Journey addJourney(BusFromBusToDateDto addJourneyDto) {
		Bus availableBuse;
		try {
			availableBuse = busRepo.findBybus_status().getFirst();
		} catch (Exception e) {
			throw new RuntimeException("No Buses are available to schedule "+e.getMessage());
		}
		Driver availableDriver;
		try {
			availableDriver = driverRepo.findByDriver_status().getFirst();
		} catch (Exception e) {
			throw new RuntimeException("No Drivers are available to schedule "+e.getMessage());
		}
		Journey newJourney = new Journey();
		newJourney.setBus_from(addJourneyDto.getBus_from());
		newJourney.setBus_id(availableBuse.getBus_id());
		newJourney.setDate(addJourneyDto.getDate());
		newJourney.setDriver_id(availableDriver.getDriver_id());
		newJourney.setTime(addJourneyDto.getTime());
		newJourney.setBus_to(addJourneyDto.getBus_to());
		Journey addedJourney = journeyRepo.save(newJourney);
		Long busId = availableBuse.getBus_id();
		busRepo.updateBusStatusWithBusID(busId);
		Long driverId = availableDriver.getDriver_id();
		driverRepo.updateDriverStatusWithDriverID(driverId);
		return addedJourney;
	}

	@Override
	public Set<String> findCities() {
	    Set<Cities> cities = journeyRepo.findCities();
	    if (cities == null || cities.isEmpty()) {
	        throw new RuntimeException("No Cities Found");
	    }
	    Set<String> citiesList = cities.stream()
	    	    .flatMap(city -> Stream.of(city.getOrigin(), city.getDestination()))
	    	    .collect(Collectors.toSet());

	    return citiesList;
	}

}
