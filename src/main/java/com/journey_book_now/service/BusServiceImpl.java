package com.journey_book_now.service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.el.util.ReflectionUtil;
import org.aspectj.weaver.reflect.ReflectionFastMatchInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.thymeleaf.spring6.expression.Fields;

import com.journey_book_now.Dto.BusFromBusToDateDto;
import com.journey_book_now.Dto.JourneyDto;
import com.journey_book_now.Dto.SeatGenderDto;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.exception.GlobalExceptionHalder;
import com.journey_book_now.exception.ResourceNotFoundException;
import com.journey_book_now.repo.BusRepo;
import com.journey_book_now.repo.DriverRepo;
import com.journey_book_now.repo.JourneyRepo;
import com.journey_book_now.repo.UserRepo;

import jakarta.annotation.security.PermitAll;

@Service
public class BusServiceImpl implements BusService {

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private JourneyRepo journeyRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private UserRepo userRepo;

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public List<Bus> busesList() {
		List<Bus> busList = new ArrayList<>();

		try {
			busList = busRepo.findAll();
		} catch (Exception e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
		System.out.println(busList);
		busList.stream()
	    .filter(bus -> bus.getAvailable_seats() != null && !bus.getAvailable_seats().isEmpty())
	    .peek(bus -> {
	        // Calculate the total available seats for each bus and set it
	        int totalAvailableSeats = (int) Arrays.stream(bus.getAvailable_seats().split(","))
	            .map(String::trim)           // Trim spaces
	            .filter(s -> !s.isEmpty()&& !s.equals("0"))   // Filter out empty strings
	            .count();                    // Count available seats
	        bus.setTotal_availableSeats(totalAvailableSeats);  // Set total available seats for the bus
	    })
	    .count();  // Trigger the stream (this line could be omitted)

		busList.stream()
	    .filter(bus -> bus.getBooked_seats() != null && !bus.getBooked_seats().isEmpty())
	    .peek(bus -> {
	        int totalBookedSeats = 0;
	        String seats = bus.getBooked_seats().trim();
	        if (!seats.isEmpty()) {
	            totalBookedSeats = (int) Arrays.stream(seats.split(","))
	                .map(String::trim)
	                .filter(s -> !s.isEmpty() && !s.equals("0"))
	                .count();
	        }
	        bus.setTotal_bookedSeats(totalBookedSeats);
	    })
	    .count();

		return busList;
	}

//	@Override
//	public Bus addBus(Bus bus) {
//		Bus newBus = busRepo.save(bus);
//		return newBus;
//	}

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public Bus addBus(String bus_num, String bus_type) {
		Bus tempBus = busRepo.findByBus_num(bus_num);
		if (tempBus != null) {
			throw new RuntimeException("Bus is already available");
		}
		Bus bus = new Bus();
		switch (bus_type) {
	    case "express":
	        bus.setTotal_seats(43);
	        bus.setAvailable_seats(IntStream.rangeClosed(1, bus.getTotal_seats())
	            .mapToObj(String::valueOf)
	            .collect(Collectors.joining(",")));
	        break;
	    case "rajadhani":
	        bus.setTotal_seats(36);
	        bus.setAvailable_seats(IntStream.rangeClosed(1, bus.getTotal_seats())
	            .mapToObj(String::valueOf)
	            .collect(Collectors.joining(",")));
	        break;
	    case "garuda":
	        bus.setTotal_seats(32);
//	        bus.setAvailable_seats(IntStream.rangeClosed(1, bus.getTotal_seats())
//	            .mapToObj(String::valueOf)
//	            .collect(Collectors.joining(",")));
	        String[] array = new String[bus.getTotal_seats()];
	        Arrays.setAll(array, i -> String.valueOf(i + 1));
	        String finalSeat = String.join(",", array);
	        bus.setAvailable_seats(finalSeat);
	        break;
	    case "super luxury":
	        bus.setTotal_seats(34);
//	        bus.setAvailable_seats(IntStream.rangeClosed(1, bus.getTotal_seats())
//	            .mapToObj(String::valueOf)
//	            .collect(Collectors.joining(",")));
	        StringBuilder sb = new StringBuilder();
	        for(int i = 1; i <= bus.getTotal_seats(); i++) {
	        	sb.append(i);
	        	
	        	if(i != bus.getTotal_seats()) {
	        		sb.append(",");
	        	}
	        }
	        System.out.println(sb.toString());
	        bus.setAvailable_seats(sb.toString());
	        break;
	}

		bus.setBus_num(bus_num);
		bus.setBus_status('Y');
		bus.setBooked_seats("0");
		bus.setBus_type(bus_type);
		Bus newBus = busRepo.save(bus);
		return newBus;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public Bus updateBusStatus(Long id, Map<String, Object> data) {
		Bus busId = busRepo.findById(id).orElseThrow(() -> new RuntimeException("No Bus Found to Update "));
		  List<String> allowedFields = List.of("bus_status", "bus_type", "available_seats", "booked_seats");
		  System.out.println(busId);
		  data.forEach((key, value) -> {
			  if (allowedFields.contains(key)) {
				  Field field = ReflectionUtils.findField(Bus.class, key);
				  if(field != null) {
					  field.setAccessible(true);
					  ReflectionUtils.setField(field, busId, convertValue(field.getType(), value));
				  }
			  }else {
				  System.out.println("Fields is not in list"+" "+ key);
			  }
			  
		  });
		  System.out.println(busId);
		  busRepo.save(busId);
		  return (busId);
	}

	private Object convertValue(Class<?> targetType, Object value) {
	    if (targetType == char.class || targetType == Character.class) {
	        return String.valueOf(value).charAt(0);
	    } else if (targetType == int.class || targetType == Integer.class) {
	        return Integer.parseInt(value.toString());
	    } else if (targetType == Long.class) {
	        return Long.parseLong(value.toString());
	    }
	    return value;
	}
	
//	@Override
//	public List<Journey> availableBuses(BusFromBusToDateDto journey) {
//		List<Journey> availableBuses = journeyRepo.availableBuses(journey.getBus_to(), journey.getBus_from(), journey.getDate(), journey.getTime());
//		if(availableBuses.isEmpty()) throw new ResourceNotFoundException(("No buses are available"));
//		return availableBuses;
//	}
	@Override
	public List<JourneyDto> availableBuses(BusFromBusToDateDto journey) {
		// ✅ Correct parameter order
		List<Object[]> results = journeyRepo.availableBuses(journey.getBus_from(), journey.getBus_to(),
				journey.getDate(), journey.getTime());

		if (results.isEmpty()) {
			throw new ResourceNotFoundException("No buses are available");
		}
		// ✅ Convert Object[] to JourneyDto
		return results.stream().map(r -> {
			List<SeatGenderDto> seatGenderList = userRepo
					.getSeatGenderByScheduleId(r[0] != null ? ((Number) r[0]).longValue() : null);

			return new JourneyDto(r[0] != null ? ((Number) r[0]).longValue() : null, // row_id
					r[1] != null ? ((Number) r[1]).longValue() : null, // bus_id
					r[2] != null ? ((Number) r[2]).longValue() : null, // driver_id
					r[3] != null ? (String) r[3] : "", // bus_from
					r[4] != null ? (String) r[4] : "", // bus_to
					r[5] != null ? LocalDate.parse(r[5].toString()) : null, // date
					r[6] != null ? LocalTime.parse(r[6].toString()) : null, // time
					r[7] != null ? (String) r[7] : "", // bus_num
					parseSeatArray(r[8]), // availableSeats
					parseSeatArray(r[9]), // bookedSeats
					r[10] != null ? (String) r[10] : "", // bus_type
					r[11] != null ? ((Number) r[11]).longValue() : null, // total_seats
					seatGenderList // ✅ Include this
			);
		}).collect(Collectors.toList());
	}

	private int[] parseSeatArray(Object seatData) {
		if (seatData == null || seatData.toString().isEmpty()) {
			return new int[0];
		}
		return java.util.Arrays.stream(seatData.toString().split(",")) // ✅ Corrected stream usage
				.mapToInt(Integer::parseInt).toArray();
	}

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	public Bus removeBus(String bus_num) {
		Bus result = busRepo.findByBus_num(bus_num);
		if (result == null) {
			throw new RuntimeException("No bus found with number: " + bus_num);
		}
		busRepo.delete(result);
		return result;
	}

}