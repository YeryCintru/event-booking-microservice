package com.eventbooking.event_service.repository;

import com.eventbooking.event_service.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository     
public interface EventRepository extends JpaRepository<Event, Long> {
    
    // Actualizamos a nivel de BD para evitar condiciones de carrera y garantizar la consistencia de los datos
    @Modifying 
    @Query ("UPDATE Event e SET e.availableCapacity = e.availableCapacity - :quantity " +
    "WHERE e.id = :id AND e.availableCapacity >= :quantity")
    int reduceCapacity(@Param("id") Long id, @Param("quantity") Integer quantity);
}
