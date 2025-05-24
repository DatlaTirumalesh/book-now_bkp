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


@Table( name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketNum;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email", nullable = false)
    private String userEmail;

    @Column(name = "user_phone_num", nullable = false)
    private int userPhoneNum;

    @Column(name = "schedule_id", nullable = false)
    private Long schedule;
    
    @Column( name = "seat_num", nullable = false)
    private int seatNum;
    
    @Column( name = "date", nullable = false) 
    private LocalDate date;
    
    @Column( name = "time", nullable = false)
    private LocalTime time;
    
    @Column( name = "driver_id")
    private Long driverId;
    
    @Column(name = "gender", nullable = false)
    private String gender;
    
}
