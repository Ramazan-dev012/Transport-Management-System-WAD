package com.example.transportsystem.service;

import com.example.transportsystem.model.BusEntity;
import com.example.transportsystem.model.PassengerEntity;
import com.example.transportsystem.model.TicketEntity;
import com.example.transportsystem.repository.BusRepository;
import com.example.transportsystem.repository.PassengerRepository;
import com.example.transportsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransportManagementService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private TicketRepository ticketRepository;

    // ==================== BUS METHODS ====================

    public List<BusEntity> getAllBuses() {
        return busRepository.findAll();
    }

    public BusEntity getBusById(Long id) {
        return busRepository.findById(id).orElse(null);
    }

    @Transactional
    public BusEntity createBus(String routeNumber, int capacity, String driverName) {
        BusEntity bus = new BusEntity(routeNumber, capacity, driverName);
        return busRepository.save(bus);
    }

    @Transactional
    public boolean deleteBus(Long id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BusEntity> getAvailableBuses() {
        return busRepository.findAvailableBuses();
    }

    public List<BusEntity> getFullBuses() {
        return busRepository.findFullBuses();
    }

    // ==================== PASSENGER METHODS ====================

    public List<PassengerEntity> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public PassengerEntity getPassengerById(Long id) {
        return passengerRepository.findById(id).orElse(null);
    }

    @Transactional
    public PassengerEntity createPassenger(String name, String phoneNumber, String destination) {
        PassengerEntity passenger = new PassengerEntity(name, phoneNumber, destination);
        return passengerRepository.save(passenger);
    }

    @Transactional
    public boolean deletePassenger(Long id) {
        if (passengerRepository.existsById(id)) {
            passengerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<PassengerEntity> getPassengersWithTickets() {
        return passengerRepository.findByHasTicketTrue();
    }

    // ==================== TICKET METHODS ====================

    @Transactional
    public boolean buyTicket(Long passengerId, Long busId) {
        BusEntity bus = getBusById(busId);
        PassengerEntity passenger = getPassengerById(passengerId);

        if (bus == null || passenger == null) {
            return false;
        }
        if (bus.isFull()) {
            return false;
        }
        if (ticketRepository.existsByPassengerAndBus(passenger, bus)) {
            return false;
        }

        int seatNumber = bus.getCurrentPassengers() + 1;
        bus.setCurrentPassengers(bus.getCurrentPassengers() + 1);
        passenger.setHasTicket(true);

        busRepository.save(bus);
        passengerRepository.save(passenger);
        ticketRepository.save(new TicketEntity(passenger, bus, seatNumber));

        return true;
    }

    public List<TicketEntity> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<TicketEntity> getTicketsByPassenger(Long passengerId) {
        PassengerEntity passenger = getPassengerById(passengerId);
        if (passenger == null) return List.of();
        return ticketRepository.findByPassenger(passenger);
    }

    public List<TicketEntity> getTicketsByBus(Long busId) {
        BusEntity bus = getBusById(busId);
        if (bus == null) return List.of();
        return ticketRepository.findByBus(bus);
    }

    // ==================== STATISTICS ====================

    public int getTotalCapacity() {
        return busRepository.findAll().stream().mapToInt(BusEntity::getCapacity).sum();
    }

    public int getTotalCurrentPassengers() {
        return busRepository.findAll().stream().mapToInt(BusEntity::getCurrentPassengers).sum();
    }

    public long getPassengersWithTicketsCount() {
        return passengerRepository.findByHasTicketTrue().size();
    }
}

