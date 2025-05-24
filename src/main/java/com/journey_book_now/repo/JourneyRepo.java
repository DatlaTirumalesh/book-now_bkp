package com.journey_book_now.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.journey_book_now.Dto.Cities;
import com.journey_book_now.Dto.JourneyDto;
import com.journey_book_now.entity.Bus;
import com.journey_book_now.entity.Journey;

public interface JourneyRepo extends JpaRepository<Journey, Long>{
	
//	@Query( value = "select * from schedule s where (s.bus_to = :bus_to) "
//			+ "AND (s.bus_from = :bus_from) "
//			+ "AND (s.date = :date) "
//			+ "AND (s.time >= :time)",nativeQuery = true)
//	List<Journey> availableBuses(@Param(value = "bus_to") String bus_from, 
//			@Param( value = "bus_from" ) String bus_to, 
//			@Param( value = "date" ) LocalDate date,
//			@Param( value = "time") LocalTime time);
//	

//	@Query( value = "select s.row_id, s.bus_id, s.driver_id, s.bus_from, s.bus_to, s.date, s.time, b.bus_num  from schedule s "+ 
//			"JOIN bus b ON s.bus_id=b.bus_id "+
//			"where (s.bus_to = :bus_to) "
//			+ "AND (s.bus_from = :bus_from) "
//			+ "AND (s.date = :date) "
//			+ "AND (s.time >= :time)",nativeQuery = true)
//	List<JourneyDto> availableBuses(@Param(value = "bus_to") String bus_from, 
//			@Param( value = "bus_from" ) String bus_to, 
//			@Param( value = "date" ) LocalDate date,
//			@Param( value = "time") LocalTime time);
//	
//	
    @Query(value = """
            SELECT s.row_id, s.bus_id, s.driver_id, s.bus_from, s.bus_to, s.date, s.time, b.bus_num, b.available_seats,
             b.booked_seats, b.bus_type, b.total_seats
            FROM schedule s 
            INNER JOIN bus b ON s.bus_id = b.bus_id 
            WHERE s.bus_from = :busFrom 
            AND s.bus_to = :busTo 
            AND s.date = :date 
            AND s.time >= :time
            """, nativeQuery = true)
        List<Object[]> availableBuses(
            @Param("busFrom") String busFrom,
            @Param("busTo") String busTo,
            @Param("date") LocalDate date,
            @Param("time") LocalTime time
        );
        
        @Query("SELECT new com.journey_book_now.Dto.Cities(j.bus_from, j.bus_to) FROM Journey j")
        Set<Cities> findCities();
}
