package com.eventbooking.event_service.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventbooking.event_service.dto.BookingCreatedEvent;
import com.eventbooking.event_service.service.EventService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class BookingEventListener {

    private final EventService eventService;

    @RabbitListener(queues = "booking.created.queue")
    public void handleBookingEvent(BookingCreatedEvent event) {
        // Handle the booking event here
        System.out.println("Received booking event: " + event);
        eventService.reduceAvailableCapacity(event.getEventId(), event.getTicketsCount());
        
    }
}
