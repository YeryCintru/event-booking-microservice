package com.eventbooking.event_service.repository;

import com.eventbooking.event_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
    
}
