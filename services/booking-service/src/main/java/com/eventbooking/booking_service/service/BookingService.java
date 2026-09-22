package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.dto.*;
import com.eventbooking.booking_service.client.EventClient;
import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.repository.BookingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventClient eventClient;

    @Transactional
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {

        // 1. Consultar el evento vía OpenFeign
        EventDTO event = eventClient.getEventById(bookingRequestDTO.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        // 2. Verificar la capacidad disponible
        if (event.getAvailableCapacity() < bookingRequestDTO.getTicketsCount()) {
            throw new RuntimeException("Not enough available capacity");
        }

        // 3. Reducir la capacidad disponible del evento
        eventClient.reduceCapacity(bookingRequestDTO.getEventId(), bookingRequestDTO.getTicketsCount());

        Booking booking = Booking.builder()
                .userId(bookingRequestDTO.getUserId())
                .eventId(bookingRequestDTO.getEventId())
                .ticketsCount(bookingRequestDTO.getTicketsCount())
                .createdAt(LocalDateTime.now())
                .status("CONFIRMED") // Valor por defecto
                .build();
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

}
