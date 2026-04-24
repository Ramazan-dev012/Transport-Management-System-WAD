package com.example.transportsystem.model;

import jakarta.persistence.*;

@Entity
@Table(name = "buses")
public class BusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "route_number", nullable = false, length = 20)
    private String routeNumber;

    @Column(nullable = false)
    private int capacity;

    @Column(name = "driver_name", nullable = false, length = 100)
    private String driverName;

    @Column(name = "current_passengers", nullable = false)
    private int currentPassengers = 0;

    public BusEntity() {}

    public BusEntity(String routeNumber, int capacity, String driverName) {
        this.routeNumber = routeNumber;
        this.capacity = capacity;
        this.driverName = driverName;
        this.currentPassengers = 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String routeNumber;
        private int capacity;
        private String driverName;
        private int currentPassengers = 0;

        private Builder() {
        }

        public Builder routeNumber(String routeNumber) {
            this.routeNumber = routeNumber;
            return this;
        }

        public Builder capacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder driverName(String driverName) {
            this.driverName = driverName;
            return this;
        }

        public Builder currentPassengers(int currentPassengers) {
            this.currentPassengers = currentPassengers;
            return this;
        }

        public BusEntity build() {
            BusEntity bus = new BusEntity();
            bus.setRouteNumber(routeNumber);
            bus.setCapacity(capacity);
            bus.setDriverName(driverName);
            bus.setCurrentPassengers(currentPassengers);
            return bus;
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRouteNumber() { return routeNumber; }
    public void setRouteNumber(String routeNumber) { this.routeNumber = routeNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getDriverName() { return driverName; }
    public void setDriverName(String driverName) { this.driverName = driverName; }

    public int getCurrentPassengers() { return currentPassengers; }
    public void setCurrentPassengers(int currentPassengers) { this.currentPassengers = currentPassengers; }

    public boolean isFull() {
        return currentPassengers >= capacity;
    }

    public int getAvailableSeats() {
        return capacity - currentPassengers;
    }
}
