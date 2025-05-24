package com.journey_book_now.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

public interface EmailPojo {
    Long getTicketNum();
    String getName();
    String getEmail();
    Long getPhoneNum();
    int getSeatNum();
    LocalDate getDate();
    LocalTime getTime();
    String getGender();
    String getFromBus();
    String getToBus();
    String getBusNum();
    Long getDriverNum();
}
