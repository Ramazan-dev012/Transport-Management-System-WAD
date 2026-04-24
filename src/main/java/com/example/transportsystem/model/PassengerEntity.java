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

    /**
     * Владелец пассажира (username аккаунта, который создал запись).
     * Нужен, чтобы обычный пользователь видел только своих пассажиров.
     */
    @Column(name = "created_by", length = 50)
    private String createdByUsername;

    public PassengerEntity() {}

    public PassengerEntity(String name, String phoneNumber, String destination) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.destination = destination;
        this.hasTicket = false;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String phoneNumber;
        private String destination;
        private boolean hasTicket = false;
        private String createdByUsername;

        private Builder() {
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder destination(String destination) {
            this.destination = destination;
            return this;
        }

        public Builder hasTicket(boolean hasTicket) {
            this.hasTicket = hasTicket;
            return this;
        }

        public Builder createdByUsername(String createdByUsername) {
            this.createdByUsername = createdByUsername;
            return this;
        }

        public PassengerEntity build() {
            PassengerEntity p = new PassengerEntity();
            p.setName(name);
            p.setPhoneNumber(phoneNumber);
            p.setDestination(destination);
            p.setHasTicket(hasTicket);
            p.setCreatedByUsername(createdByUsername);
            return p;
        }
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

    public String getCreatedByUsername() { return createdByUsername; }
    public void setCreatedByUsername(String createdByUsername) { this.createdByUsername = createdByUsername; }

    public void buyTicket() { this.hasTicket = true; }
}
