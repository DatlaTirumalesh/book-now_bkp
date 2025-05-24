package com.journey_book_now.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.journey_book_now.Dto.EmailPojo;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.entity.Journey;
import com.journey_book_now.entity.User;
import com.journey_book_now.helper.StringToIntArrayConverter;
import com.journey_book_now.repo.BusRepo;
import com.journey_book_now.repo.JourneyRepo;
import com.journey_book_now.repo.UserRepo;

import jakarta.mail.MessagingException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private JourneyRepo journeyRepo;

	@Autowired
	private BusRepo busRepo;
	
	@Autowired
	private StringToIntArrayConverter stringToIntArrayConverter;
	
	@Autowired
	private EmailService emailService;

	@Override
	@PreAuthorize("hasRole('ADMIN')")
	public List<User> findAll() {
		List<User> usersList = userRepo.findAll();
		if (usersList.isEmpty())
			throw new RuntimeException("User List is empty");
		return usersList;
	}

//	@Override
//	public User userTicketBooking(User user) {
//		Journey journeyDetails = journeyRepo.findById(user.getSchedule())
//				.orElseThrow(() -> new RuntimeException("No schedule ID Found"));
//		Bus userBus = busRepo.findById(journeyDetails.getBus_id())
//				.orElseThrow(() -> new RuntimeException("No Bus found with given journey details"));
//		Bus bus = busRepo.findByBusId(userBus.getBus_id());
//		if (bus != null) {
//			int[] availableSeatsArray = bus.getavailable_seatsAsInt();
//			int[] bookedSeatsArray = bus.getbooked_seatsAsInt();
//			int remove = user.getSeatNum();
//			availableSeatsArray = Arrays.stream(availableSeatsArray).filter(i -> i != remove).toArray();
//			int[] updatedBookedArray;
//			if (bookedSeatsArray.length == 0 || bookedSeatsArray[0] == 0) {
//				updatedBookedArray = new int[1];
//				updatedBookedArray[0] = user.getSeatNum();
//			} else {
//				updatedBookedArray = Arrays.copyOf(bookedSeatsArray, bookedSeatsArray.length + 1);
//				updatedBookedArray[bookedSeatsArray.length] = user.getSeatNum();
//				updatedBookedArray[bookedSeatsArray.length] = remove;
//			}
//			Bus updatedBus = new Bus();
//			updatedBus.setAvailable_seats(bus.setavailable_seatsFromInt(availableSeatsArray));
//			updatedBus.setBooked_seats(bus.setbooked_seatsFromInt(updatedBookedArray));
//			updatedBus.setBus_id(bus.getBus_id());
//			updatedBus.setBus_num(bus.getBus_num());
//			updatedBus.setBus_status(bus.getBus_status());
//			updatedBus.setBus_type(bus.getBus_type());
//			updatedBus.setTotal_seats(bus.getTotal_seats());
//			busRepo.save(updatedBus);
//		} else {
//			throw new RuntimeException("Bus details are null");
//		}
//		List<User> validateUser = userRepo.findAll();
//		validateUser.forEach(user1 -> {
//			if (user1.getSchedule() == user.getSchedule() && user1.getSeatNum() == user.getSeatNum()) {
//				throw new RuntimeException("Ticket is already booked");
//			}
//		});
//		if(user.getSeatNum() > userBus.getTotal_seats() ) {
//			throw new RuntimeException(user.getSeatNum()+" : is not available in "+userBus.getBus_num());
//		}
//		User newUser = userRepo.save(user);
//		return newUser;
//	}
	
	@Override
	public User userTicketBooking(User user) {
		Journey journeyDetails = journeyRepo.findById(user.getSchedule())
				.orElseThrow(() -> new RuntimeException("No schedule ID Found"));
		Bus userBus = busRepo.findById(journeyDetails.getBus_id())
				.orElseThrow(() -> new RuntimeException("No Bus found with given journey details"));
		List<User> validateUser = userRepo.findAll();
		validateUser.forEach(user1 -> {
			if (user1.getSchedule().equals(user.getSchedule()) && user1.getSeatNum() == user.getSeatNum()){
				throw new RuntimeException("Ticket is already booked User :"+user1.getUserEmail()+" User Name : "+user1.getUserName());
			}
		});
		Bus bus = busRepo.findByBusId(userBus.getBus_id());
		int[] availableSeatsArray = stringToIntArrayConverter.getArrayAsInt(bus.getAvailable_seats());
		int[] bookedSeatsArray = stringToIntArrayConverter.getArrayAsInt(bus.getBooked_seats());
		if(user.getSeatNum() > userBus.getTotal_seats() || Arrays.stream(bookedSeatsArray).anyMatch(num -> num == user.getSeatNum())) {
			throw new RuntimeException(user.getSeatNum()+" : is not available in "+userBus.getBus_num());
		}
		Bus updatedBus = new Bus();		
			int remove = user.getSeatNum();
			availableSeatsArray = Arrays.stream(availableSeatsArray).filter(i -> i != remove).toArray();
			int[] updatedBookedArray;
			if (bookedSeatsArray.length == 0 || bookedSeatsArray[0] == 0) {
				updatedBookedArray = new int[1];
				updatedBookedArray[0] = user.getSeatNum();
			} else {
				updatedBookedArray = Arrays.copyOf(bookedSeatsArray, bookedSeatsArray.length + 1);
				updatedBookedArray[bookedSeatsArray.length] = user.getSeatNum();
				updatedBookedArray[bookedSeatsArray.length] = remove;
			}
			updatedBus.setAvailable_seats(stringToIntArrayConverter.setArrayFromInt(availableSeatsArray));
			updatedBus.setBooked_seats(stringToIntArrayConverter.setArrayFromInt((updatedBookedArray)));
			updatedBus.setBus_id(bus.getBus_id());
			updatedBus.setBus_num(bus.getBus_num());
			updatedBus.setBus_status(bus.getBus_status());
			updatedBus.setBus_type(bus.getBus_type());
			updatedBus.setTotal_seats(bus.getTotal_seats());
		user.setDate(LocalDate.now());
		user.setTime(LocalTime.now());
		busRepo.save(updatedBus);
		user.setDriverId(journeyDetails.getDriver_id());
		User newUser = userRepo.save(user);
		userDetailsWithTicketNum(user.getTicketNum());
		return newUser;
		
	}


	@Override
	public Map<String, Object> userTicketDetails(Long id) {
		User userDetails = userRepo.findById(id).orElseThrow(() -> new RuntimeException("No User Found"));
		Journey journeyDetails = journeyRepo.findById(userDetails.getSchedule())
				.orElseThrow(() -> new RuntimeException("No schedule ID Found"));
		Map<String, Object> result = new HashMap<>();
		result.put("User", userDetails);
		result.put("journey", journeyDetails);
		return result;
	}

	@Override
//	@PreAuthorize("hasRole('ADMIN')")
	public EmailPojo userDetailsWithTicketNum(Long id) {
		EmailPojo body = userRepo.findUserDetailsWithTicketNum(id);
		
		if(body == null) {
			throw new RuntimeException("User ticket is not Found");
		}
		 // Convert POJO to a Map for Thymeleaf
        Map<String, Object> emailVariables = new HashMap<>();
        emailVariables.put("ticketNum", body.getTicketNum());
        emailVariables.put("name", body.getName());
        emailVariables.put("email", body.getEmail());
        emailVariables.put("phoneNum", body.getPhoneNum());
        emailVariables.put("seatNum", body.getSeatNum());
        emailVariables.put("date", body.getDate());
        emailVariables.put("time", body.getTime());
        emailVariables.put("gender", body.getGender());
        emailVariables.put("fromBus", body.getFromBus());
        emailVariables.put("toBus", body.getToBus());
        emailVariables.put("busNum", body.getBusNum());
        emailVariables.put("driverNum", body.getDriverNum());

        emailService.sendEmailWithThymeleaf(body.getEmail(), 
		    "Your Bus Ticket Confirmation - Aero", 
		    "booking-confirmation", // Template name (Thymeleaf file)
		    emailVariables);

        return body;
	}
}
