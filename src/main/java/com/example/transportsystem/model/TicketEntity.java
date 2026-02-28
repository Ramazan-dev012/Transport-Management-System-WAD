package com.example.transportsystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class TicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "passenger_id", nullable = false)
    private PassengerEntity passenger;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bus_id", nullable = false)
    private BusEntity bus;

    @Column(name = "purchased_at", nullable = false)
    private LocalDateTime purchasedAt;

    @Column(name = "seat_number")
    private int seatNumber;

    public TicketEntity() {}

    public TicketEntity(PassengerEntity passenger, BusEntity bus, int seatNumber) {
        this.passenger = passenger;
        this.bus = bus;
        this.seatNumber = seatNumber;
        this.purchasedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PassengerEntity getPassenger() { return passenger; }
    public void setPassenger(PassengerEntity passenger) { this.passenger = passenger; }

    public BusEntity getBus() { return bus; }
    public void setBus(BusEntity bus) { this.bus = bus; }

    public LocalDateTime getPurchasedAt() { return purchasedAt; }
    public void setPurchasedAt(LocalDateTime purchasedAt) { this.purchasedAt = purchasedAt; }

    public int getSeatNumber() { return seatNumber; }
    public void setSeatNumber(int seatNumber) { this.seatNumber = seatNumber; }
}

