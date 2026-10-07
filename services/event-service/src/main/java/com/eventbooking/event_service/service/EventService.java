package com.eventbooking.event_service.service;

import com.eventbooking.event_service.model.Event;
import com.eventbooking.event_service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    @Transactional
    public void reduceAvailableCapacity(Long eventId, Integer quantity){
        if (quantity <= 0){
            throw new IllegalArgumentException("La cantidad de entradas debe ser mayor a cero");
        }


        int rowsUpdated = eventRepository.reduceCapacity(eventId,quantity);

        if (rowsUpdated == 0) {
            throw new IllegalStateException("No hay aforo suficiente disponible o el evento no existe.");
        }
    }


    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event event){
        event.setAvailableCapacity(event.getTotalCapacity());
        return eventRepository.save(event);
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Event not found"));
    }


    
}
