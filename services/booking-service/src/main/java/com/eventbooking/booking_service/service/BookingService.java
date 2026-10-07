package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.dto.*;
import com.eventbooking.booking_service.client.*;
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
    private final AuthClient authClient;

    @Transactional
    public Booking createBooking(BookingRequestDTO bookingRequestDTO, String authHeader) {

        // 1. Extraer y validar el Token JWT con auth-service
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
                throw new RuntimeException("Cabecera AUthorization no proporcionada o formato inválido");
        }

        String token = authHeader.substring(7);

        try{
            Boolean isValid = authClient.validateToken(token);
            if (isValid == null || !isValid) {
                throw new RuntimeException("Token inválido o expirado");
            }
        } catch (Exception e){
            throw new RuntimeException("Error al autenticar con auth-service" + e.getMessage());
        }


        // 2. Consultar el evento vía OpenFeign
        EventDTO event = eventClient.getEventById(bookingRequestDTO.getEventId());
        if (event == null) {
            throw new IllegalArgumentException("Event not found");
        }

        // 3. Verificar la capacidad disponible
        if (event.getAvailableCapacity() < bookingRequestDTO.getTicketsCount()) {
            throw new RuntimeException("Not enough available capacity");
        }

        // 4. Reducir la capacidad disponible del evento
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
