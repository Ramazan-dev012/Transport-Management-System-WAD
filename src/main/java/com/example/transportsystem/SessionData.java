package com.example.transportsystem;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SessionData implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Bus> buses;
    private final List<Passenger> passengers;
    private int busIdCounter;
    private int passengerIdCounter;
    private final long createdAt;

    public SessionData() {
        this.buses = new ArrayList<>();
        this.passengers = new ArrayList<>();
        this.createdAt = System.currentTimeMillis();

        // Начальные данные для каждой новой сессии
        this.busIdCounter = 1;
        this.passengerIdCounter = 1;

        buses.add(new Bus(busIdCounter++, "101", 40, "Ivan Petrov"));
        buses.add(new Bus(busIdCounter++, "202", 50, "Maria Sidorova"));
        buses.add(new Bus(busIdCounter++, "303", 30, "Sergey Ivanov"));

        passengers.add(new Passenger(passengerIdCounter++, "Alex Smith", "+123456789", "Station A"));
        passengers.add(new Passenger(passengerIdCounter++, "John Doe", "+987654321", "Station B"));
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getNextBusId() {
        return busIdCounter++;
    }

    public int getNextPassengerId() {
        return passengerIdCounter++;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}

