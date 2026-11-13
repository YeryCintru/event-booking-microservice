package com.eventbooking.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingCreatedEvent {
    private Long bookingId;
    private Long userId;
    private Long eventId;
    private LocalDateTime createdAt;

}
