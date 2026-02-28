package com.example.transportsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "passengers")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(length = 100)
    private String destination;

    @Column(name = "has_ticket", nullable = false)
    private boolean hasTicket = false;

    public PassengerEntity() {}

    public PassengerEntity(String name, String phoneNumber, String destination) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.destination = destination;
        this.hasTicket = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public boolean isHasTicket() { return hasTicket; }
    public void setHasTicket(boolean hasTicket) { this.hasTicket = hasTicket; }

    public void buyTicket() { this.hasTicket = true; }
}

