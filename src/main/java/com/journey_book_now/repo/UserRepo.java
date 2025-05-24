package com.journey_book_now.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.journey_book_now.Dto.EmailPojo;
import com.journey_book_now.Dto.SeatGenderDto;
import com.journey_book_now.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

	@Query(value = """
		    SELECT
		        u.ticket_num AS ticketNum,
		        u.user_name AS name,
		        u.user_email AS email,
		        u.user_phone_num AS phoneNum,
		        u.seat_num AS seatNum,
		        u.date AS date,
		        u.time AS time,
		        u.gender AS gender,
		        s.bus_from AS fromBus,
		        s.bus_to AS toBus,
		        s.bus_id AS busNum,
		        d.phone_num AS driverNum
		    FROM user u
		    JOIN schedule s ON u.schedule_id = s.row_id
		    JOIN driver d ON u.driver_id = d.driver_id
		    WHERE ticket_num = :ticket_num
		""", nativeQuery = true)
		EmailPojo findUserDetailsWithTicketNum(@RequestParam Long ticket_num);
	
	@Query("SELECT new com.journey_book_now.Dto.SeatGenderDto(u.seatNum, u.gender) FROM User u WHERE u.schedule = :scheduleId")
	List<SeatGenderDto> getSeatGenderByScheduleId(@Param("scheduleId") Long scheduleId);

	
}
