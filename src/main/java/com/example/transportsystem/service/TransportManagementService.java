package com.example.transportsystem.service;

import com.example.transportsystem.factory.TransportEntityFactory;
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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransportManagementService {

    private final TransportEntityFactory entityFactory;

    public TransportManagementService(TransportEntityFactory entityFactory) {
        this.entityFactory = entityFactory;
    }

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
        BusEntity bus = entityFactory.newBus(routeNumber, capacity, driverName);
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
        PassengerEntity passenger = entityFactory.newPassenger(name, phoneNumber, destination);
        return passengerRepository.save(passenger);
    }

    @Transactional
    public PassengerEntity createPassenger(String name, String phoneNumber, String destination, String createdByUsername) {
        PassengerEntity passenger = entityFactory.newPassenger(name, phoneNumber, destination);
        passenger.setCreatedByUsername(createdByUsername);
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

    /**
     * Для обычного пользователя: показываем только пассажиров, у которых ещё нет билета.
     * (Админ, при необходимости, продолжает видеть всех.)
     */
    public List<PassengerEntity> getPassengersEligibleForPurchase() {
        return passengerRepository.findByHasTicketFalse();
    }

    public List<PassengerEntity> getPassengersByOwner(String username) {
        return passengerRepository.findByCreatedByUsernameOrderByIdDesc(username);
    }

    public List<PassengerEntity> getPassengersEligibleForPurchase(String username) {
        return passengerRepository.findByCreatedByUsernameAndHasTicketFalseOrderByIdDesc(username);
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
        ticketRepository.save(entityFactory.newTicket(passenger, bus, seatNumber));

        return true;
    }

    @Transactional
    public Map<String, Object> buyTicketAndGetDetails(Long passengerId, Long busId) {
        BusEntity bus = busRepository.findById(busId)
                .orElseThrow(() -> new IllegalStateException("Автобус не найден."));
        PassengerEntity passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new IllegalStateException("Пассажир не найден."));

        if (bus.isFull()) {
            throw new IllegalStateException("В автобусе нет свободных мест.");
        }
        if (passenger.isHasTicket()) {
            throw new IllegalStateException("У этого пассажира уже есть билет.");
        }

        int seatNumber = bus.getCurrentPassengers() + 1;
        bus.setCurrentPassengers(bus.getCurrentPassengers() + 1);
        passenger.setHasTicket(true);

        busRepository.save(bus);
        passengerRepository.save(passenger);
        ticketRepository.save(entityFactory.newTicket(passenger, bus, seatNumber));

        // Список сопассажиров: все пассажиры, у которых есть билет на этот автобус.
        List<String> copassengers = ticketRepository.findByBus(bus).stream()
                .map(TicketEntity::getPassenger)
                .map(PassengerEntity::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return Map.of(
                "passengerName", passenger.getName(),
                "busRoute", bus.getRouteNumber(),
                "copassengers", copassengers
        );
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
