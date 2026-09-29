package com.eventbooking.booking_service.client;

import com.eventbooking.booking_service.dto.EventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "event-service", url = "http://localhost:8082/api/events")
public interface EventClient {
    
    @GetMapping("/{id}")
    EventDTO getEventById(@PathVariable("id") Long id);

    @PutMapping("/{id}/reserve")
    void reduceCapacity(@PathVariable("id") Long id, @RequestParam("quantity") Integer quantity);
}
