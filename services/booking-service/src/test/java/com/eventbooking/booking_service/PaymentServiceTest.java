package com.eventbooking.booking_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.eventbooking.booking_service.service.PaymentService;


import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;

public class PaymentServiceTest {
    
    private PaymentService paymentService;

    @BeforeEach 
    void setUp() {
        paymentService = new PaymentService();
        paymentService.init();
    }

    @Test
    @DisplayName("Debe lanzar IllegalArgumentException si el importe es nulo o menor/igual a 0")
    void shouldThrowExceptionWhenAmountIsValid() {

        //Test con importe nulo
        assertThrows(IllegalArgumentException.class, () ->
            paymentService.createPaymentIntent(null,"eur")
        );

        //Test con importe cero
        assertThrows(IllegalArgumentException.class, () ->
            paymentService.createPaymentIntent(0L,"eur")
        );

        //Test con importe negativo
        assertThrows(IllegalArgumentException.class, () ->
            paymentService.createPaymentIntent(-500L,"eur")
        );
    }

    @ParameterizedTest 
    @ValueSource(strings = {"", "  "})
    @DisplayName("Debe lanzar IllegalArgumentException si la moneda está vacia")
    void shouldThrowExceptionWhenCurrectyIsInvalid(String invalidCurrency) {
        assertThrows(IllegalArgumentException.class, () ->
            paymentService.createPaymentIntent(1500L,invalidCurrency)
        );
    }

    @Test
    @DisplayName("Debe retornar un PaymentIntent mockeado exitosamente sin conectar a la red")
    void shouldReturnPaymentIntentSuccessfully() throws StripeException {
        // Given
        Long amount = 2000L; // 20.00 EUR
        String currency = "eur";

        PaymentIntent mockIntent = Mockito.mock(PaymentIntent.class);
        Mockito.when(mockIntent.getClientSecret()).thenReturn("pi_test_secret_12345");

        // When: Interceptamos la llmada estática a PaymentIntent.create()
        try (MockedStatic<PaymentIntent> mockedStripe = Mockito.mockStatic(PaymentIntent.class)) {
            mockedStripe.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class)))
                        .thenReturn(mockIntent);
            
            PaymentIntent result = paymentService.createPaymentIntent(amount, currency);
            
            // Then
            assertNotNull(result);
            assertEquals("pi_test_secret_12345", result.getClientSecret());

            // Verificamos que PaymentIntent.create() fue invocado exactamente 1 vez
            mockedStripe.verify(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class)));

        }
    }
}
