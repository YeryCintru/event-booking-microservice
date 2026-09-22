package com.eventbooking.booking_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private Integer ticketsCount;

    @Column(nullable = false)
    private String status; // Ej: "CONFIRMED", "CANCELLED", "PENDING"

    private LocalDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = "CONFIRMED"; // Valor por defecto
        }
    }

}