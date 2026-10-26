package com.eventbooking.booking_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "auth-service", 
            url = "${API_URL:http://localhost:8080}/api/auth")
public interface AuthClient {
    
    @GetMapping ("/validate")
    boolean validateToken(@RequestParam("token") String token);
}
