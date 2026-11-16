package com.eventbooking.event_service.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.eventbooking.event_service.dto.BookingCreatedEvent;

@Component
public class BookingEventListener {

    @RabbitListener(queues = "booking.created.queue")
    public void handleBookingEvent(BookingCreatedEvent event) {
        // Handle the booking event here
        System.out.println("Received booking event: " + event);
        System.out.println("Received booking event: " + event.getBookingId() + ", User ID: " + event.getUserId() + ", Event ID: " + event.getEventId() + ", Created At: " + event.getCreatedAt());
    }
}
