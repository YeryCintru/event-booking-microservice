package com.eventbooking.booking_service.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

import com.eventbooking.booking_service.model.Booking;

@Data
public class BookingDTO {
    private Long Id;
    private Long userId;
    private Long eventId;
    private Integer ticketsCount;
    private String status;
    private LocalDateTime createdAt;

    public BookingDTO(Booking booking){
        this.Id = booking.getId();
        this.userId = booking.getUserId();
        this.eventId = booking.getEventId();
        this.ticketsCount = booking.getTicketsCount();
        this.status = booking.getStatus();
        this.createdAt = booking.getCreatedAt();
    }
}
