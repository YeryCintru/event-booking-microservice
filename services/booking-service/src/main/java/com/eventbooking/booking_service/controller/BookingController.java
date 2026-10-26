package com.eventbooking.booking_service.controller;

import com.eventbooking.booking_service.model.Booking;
import com.eventbooking.booking_service.service.BookingService;
import com.eventbooking.booking_service.service.PaymentService;
import com.eventbooking.booking_service.dto.BookingRequestDTO;
import com.eventbooking.booking_service.dto.BookingDTO;
import com.eventbooking.booking_service.dto.PaymentRequestDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final PaymentService paymentService;

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

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String,String>> createPaymentIntent(@RequestBody PaymentRequestDTO request){
        try{
            
            Long amuontInCents = (long) (request.getAmount() * 100);
            PaymentIntent intent = paymentService.createPaymentIntent(amuontInCents, "eur");

            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", intent.getClientSecret());
            return ResponseEntity.ok(response);

        } catch (StripeException e) {
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", e.getMessage()));
        }
    }

}