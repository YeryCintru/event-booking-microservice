package com.eventbooking.booking_service.controller;

import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.service.BookingService;
import com.eventbooking.booking_service.dto.BookingRequestDTO;
import com.eventbooking.booking_service.dto.BookingDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:5173", "http://localhost:80", "http://localhost" })
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(
        @RequestBody BookingRequestDTO bookingRequestDTO,
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

        Booking createdBooking = bookingService.createBooking(bookingRequestDTO, authHeader);
        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<Booking>> getBookingsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingsByUserId(id));
    }

    @GetMapping("/{bookingId}/ticket")
        public ResponseEntity<byte[]> downloadTicket(@PathVariable Long bookingId) {
        
            byte[] pdfBytes = bookingService.downloadTicket(bookingId);
            // Devolver como descarga el archivo (application/pdf)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "ticket-" + bookingId + ".pdf");

            return new ResponseEntity<>(pdfBytes,headers,HttpStatus.OK);
    }

}