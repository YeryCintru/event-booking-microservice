package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.dto.*;
import com.eventbooking.booking_service.client.*;
import com.eventbooking.booking_service.config.RabbitMQConfig;
import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.repository.BookingRepository;
import com.eventbooking.booking_service.service.TicketGeneratorService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;

import javax.print.attribute.standard.Media;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final EventClient eventClient;
    private final AuthClient authClient;
    private final TicketGeneratorService ticketgeneratorService;
    @Autowired
    private final RabbitTemplate rabbitTemplate;

    /**
     * Crea una nueva reserva de evento y la guarda en la base de datos.
     * @param bookingRequestDTO
     * @param authHeader
     * @return
     */
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

        // 5. Crear la reserva y guardarla en la base de datos
        Booking booking = Booking.builder()
                .userId(bookingRequestDTO.getUserId())
                .eventId(bookingRequestDTO.getEventId())
                .ticketsCount(bookingRequestDTO.getTicketsCount())
                .createdAt(LocalDateTime.now())
                .status("CONFIRMED") // Valor por defecto
                .build();
        
        Booking savedBooking = bookingRepository.save(booking);

        // 6. Publicar un evento de "Booking Created" en RabbitMQ
        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent(
                savedBooking.getId(),
                savedBooking.getUserId(),
                savedBooking.getEventId(),
                savedBooking.getCreatedAt()
        );

        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.ROUTING_KEY, 
            bookingCreatedEvent);

        return savedBooking;
    }

    /**
     * Obtiene todas las reservas de un usuario específico.
     * @param userId
     * @return
     */
    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    /**
     * Genera un PDF de la entrada para una reserva específica.
     * @param bookingId
     * @return
     */
    public byte[] downloadTicket(Long bookingId){
        Booking booking = bookingRepository.getById(bookingId);
        BookingDTO bookingDTO = new BookingDTO(booking);
        EventDTO event = eventClient.getEventById(booking.getEventId());
        byte[] pdfBytes = null;
        try{
        pdfBytes = ticketgeneratorService.generateTicketPdf(bookingDTO, event);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la entrada en PDF");
        }
        return pdfBytes;

    }



}
