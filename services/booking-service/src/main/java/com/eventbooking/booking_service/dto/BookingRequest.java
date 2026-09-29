package com.eventbooking.booking_service.dto;

import lombok.Data;

@Data
public class BookingRequest {
    private Long userId;
    private Long eventId;
    private Integer ticketsQuantity;
}
