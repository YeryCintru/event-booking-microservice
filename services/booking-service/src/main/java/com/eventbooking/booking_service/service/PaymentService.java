package com.eventbooking.booking_service.service;



import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import jakarta.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service 
public class PaymentService {
    
    @Value("${stripe.apiKey}")
    private String secretKey;

    @PostConstruct 
    public void init(){
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(Long amountInCents, String currency) throws StripeException {
        
        // Comprobaciones básicas
        if (amountInCents == null || amountInCents <= 0) {
            throw new IllegalArgumentException("El importe debe ser mayor a 0 céntimos");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("La moneda es obligatoria");
        }

        // PaymentIntent
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(amountInCents) // Ej. 1500 = 15.00 EUR
            .setCurrency(currency)    // "eur" o "usd"
            .setAutomaticPaymentMethods(
                PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                    .setEnabled(true)
                    .build()
            )
            .build();

        return PaymentIntent.create(params);
    }

}
