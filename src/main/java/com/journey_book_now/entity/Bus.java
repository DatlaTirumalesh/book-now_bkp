package com.journey_book_now.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table( name = "bus")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Bus {
	
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	@Column(name = "bus_id", nullable = false)
	private Long bus_id;
	
	@Column( name = "bus_num", nullable = false)
	private String bus_num;
	
	@Column( name = "status", nullable = false)
	private char bus_status;
	
	@Column( name = "total_seats", nullable = false)
	private int total_seats;
	
	@Column( name = "available_seats" , nullable = false)
	private String available_seats;
	
	@Column( name = "booked_seats", nullable = false) 
	private String booked_seats;
	
	@Column( name = "bus_type", nullable = false) 
	private String bus_type;

	@Transient
	private int total_availableSeats;
	@Transient
	private int total_bookedSeats;
	
    // Helper methods to convert between String and int array
//    public int[] getavailable_seatsAsInt() {
//        if (this.available_seats == null || available_seats.isEmpty()) {
//            return new int[0];
//        }
//        return Arrays.stream(available_seats.split(","))
//                     .mapToInt(Integer::parseInt)
//                     .toArray();
//    }
//
//    public String setavailable_seatsFromInt(int[] available_seats) {
//        return Arrays.stream(available_seats)
//                              .mapToObj(String::valueOf)
//                              .collect(Collectors.joining(","));
//    }
    
    // Helper methods to convert between String and int array
//    public int[] getbooked_seatsAsInt() {
//        if (this.booked_seats == null || booked_seats.isEmpty()) {
//        	int[] newArr = new int[0];
//            return newArr;
//        }
//        return Arrays.stream(booked_seats.split(","))
//                     .mapToInt(Integer::parseInt)
//                     .toArray();
//    }
//
//
//
//    public String setbooked_seatsFromInt(int[] booked_seats) {
//        return Arrays.stream(booked_seats)
//                              .mapToObj(String::valueOf)
//                              .collect(Collectors.joining(","));
//    }
    
}
