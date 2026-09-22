package com.eventbooking.booking_service.controller;

import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.service.BookingService;
import com.eventbooking.booking_service.dto.BookingRequestDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
        Booking createdBooking = bookingService.createBooking(bookingRequestDTO);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(id));
    }

}