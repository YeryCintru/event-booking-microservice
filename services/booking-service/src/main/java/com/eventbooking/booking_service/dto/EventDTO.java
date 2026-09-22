package com.eventbooking.booking_service.dto;

import lombok.Data;

@Data
public class EventDTO {
    private Long id;
    private String title;
    private Integer totalCapacity;
    private int availableCapacity;
}