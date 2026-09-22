package com.eventbooking.booking_service.service;

import com.eventbooking.booking_service.dto.BookingRequestDTO;
import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.repository.BookingRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Transactional
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        Booking booking = Booking.builder()
                .userId(bookingRequestDTO.getUserId())
                .eventId(bookingRequestDTO.getEventId())
                .ticketsCount(bookingRequestDTO.getTicketsCount())
                .status("CONFIRMED") // Valor por defecto
                .build();
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

}
