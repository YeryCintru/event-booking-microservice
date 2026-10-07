package com.eventbooking.booking_service.dto;

import lombok.Data;

@Data
public class BookingRequestDTO {
    private Long userId;
    private Long eventId;
    private Integer ticketsCount;
}
