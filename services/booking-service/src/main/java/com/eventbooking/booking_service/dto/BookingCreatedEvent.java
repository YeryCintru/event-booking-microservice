package com.eventbooking.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookingCreatedEvent {
    private Long eventId;
    private Integer ticketsCount;

}
