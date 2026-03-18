package com.journey_book_now.Dto;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class JourneyDto {

	private Long rowId;
    private Long busId;
    private Long driverId;
    private String busFrom;
    private String busTo;
    private LocalDate date;
    private LocalTime time;
    private String busNum; // From bus table
    private int[] availableSeats;
    private int[] bookedSeats;
    private String busType;
    private Long totalSeats;
    private List<SeatGenderDto> seatGendersList;


    // Getters and Setters
    
    
}