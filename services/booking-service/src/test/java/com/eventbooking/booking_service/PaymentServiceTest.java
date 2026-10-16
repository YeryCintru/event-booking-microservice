package com.eventbooking.booking_service;

import org.junit.jupiter.api.BeforeEach;

import com.eventbooking.booking_service.service.PaymentService;

public class PaymentServiceTest {
    
    private PaymentService paymentService;

    @BeforeEach 
    void setUp() {
        paymentService = new PaymentService();
    }
}
