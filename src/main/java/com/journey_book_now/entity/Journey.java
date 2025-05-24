package com.journey_book_now.entity;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table( name = "schedule")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Journey {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column( name = "row_id")
	private Long row_id;
	
	@Column( name = "bus_id", nullable = false)
	private Long bus_id;
	
	@Column( name = "driver_id", nullable = false)
	private Long driver_id;
	
	@Column( name = "bus_from", nullable = false)
	private String bus_from;
	
	@Column( name = "bus_to", nullable = false)
	private String bus_to;
	
    @Column( name = "date", nullable = false) 
    private LocalDate date;
    
    @Column( name = "time", nullable = false)
    private LocalTime time;
	

}
