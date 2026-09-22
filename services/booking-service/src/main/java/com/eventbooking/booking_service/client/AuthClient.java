package com.eventbooking.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "auth-service", url = "http://localhost:8081/api/auth")
public interface AuthClient {
    
    @GetMapping ("/validate")
    boolean validateToken(@RequestHeader("Authorization") String token);
}
