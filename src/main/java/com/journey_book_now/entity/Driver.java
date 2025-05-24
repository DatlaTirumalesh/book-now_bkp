package com.journey_book_now.entity;

import java.util.Date;

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


@Table( name = "driver")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Driver {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name ="driver_id", nullable = false)
	private Long driver_id;
	
	@Column(name = "phone_num", nullable = false)
	private int phone_num;
	
	@Column(name = "status", nullable = false)
	private char driver_status;

}
