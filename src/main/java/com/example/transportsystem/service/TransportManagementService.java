package com.example.transportsystem.service;

import com.example.transportsystem.Bus;
import com.example.transportsystem.Passenger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportManagementService {
    private final List<Bus> buses = new ArrayList<>();
    private final List<Passenger> passengers = new ArrayList<>();
    private int busIdCounter = 1;
    private int passengerIdCounter = 1;

    public TransportManagementService() {

        buses.add(new Bus(busIdCounter++, "101", 40, "Ivan Petrov"));
        buses.add(new Bus(busIdCounter++, "202", 50, "Maria Sidorova"));
        buses.add(new Bus(busIdCounter++, "303", 30, "Sergey Ivanov"));

        passengers.add(new Passenger(passengerIdCounter++, "Alex Smith", "+123456789", "Station A"));
        passengers.add(new Passenger(passengerIdCounter++, "John Doe", "+987654321", "Station B"));
    }


    public List<Bus> getAllBuses() {
        return new ArrayList<>(buses);
    }

    public Bus getBusById(int id) {
        return buses.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Bus createBus(String routeNumber, int capacity, String driverName) {
        Bus newBus = new Bus(busIdCounter++, routeNumber, capacity, driverName);
        buses.add(newBus);
        return newBus;
    }

    public boolean deleteBus(int id) {
        return buses.removeIf(bus -> bus.getId() == id);
    }

    public List<Bus> getAvailableBuses() {
        return buses.stream()
                .filter(b -> !b.isFull())
                .collect(Collectors.toList());
    }

    public List<Bus> getFullBuses() {
        return buses.stream()
                .filter(Bus::isFull)
                .collect(Collectors.toList());
    }

    public List<Passenger> getAllPassengers() {
        return new ArrayList<>(passengers);
    }

    public Passenger getPassengerById(int id) {
        return passengers.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Passenger createPassenger(String name, String phoneNumber, String destination) {
        Passenger newPassenger = new Passenger(passengerIdCounter++, name, phoneNumber, destination);
        passengers.add(newPassenger);
        return newPassenger;
    }

    public boolean deletePassenger(int id) {
        return passengers.removeIf(passenger -> passenger.getId() == id);
    }

    public List<Passenger> getPassengersWithTickets() {
        return passengers.stream()
                .filter(Passenger::isHasTicket)
                .collect(Collectors.toList());
    }

    public boolean buyTicket(int passengerId, int busId) {
        Bus bus = getBusById(busId);
        Passenger passenger = getPassengerById(passengerId);

        if (bus != null && passenger != null && !bus.isFull()) {
            bus.addPassenger();
            passenger.buyTicket();
            return true;
        }
        return false;
    }

    // Statistics
    public int getTotalCapacity() {
        return buses.stream().mapToInt(Bus::getCapacity).sum();
    }

    public int getTotalCurrentPassengers() {
        return buses.stream().mapToInt(Bus::getCurrentPassengers).sum();
    }

    public long getPassengersWithTicketsCount() {
        return passengers.stream().filter(Passenger::isHasTicket).count();
    }
}

