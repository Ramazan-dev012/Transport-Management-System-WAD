package com.example.transportsystem.controller;

import com.example.transportsystem.Bus;
import com.example.transportsystem.Passenger;
import com.example.transportsystem.service.TransportManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/transport")
public class TransportRestController {

    @Autowired
    private TransportManagementService service;


    @GetMapping("/buses")
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(service.getAllBuses());
    }


    @GetMapping("/buses/{id}")
    public ResponseEntity<?> getBusById(@PathVariable int id) {
        Bus bus = service.getAllBuses().stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);

        if (bus == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Автобус с ID " + id + " не найден"));
        }
        return ResponseEntity.ok(bus);
    }

    @PostMapping("/buses")
    public ResponseEntity<Bus> createBus(@RequestBody BusRequest request) {
        Bus bus = service.createBus(request.getRouteNumber(), request.getCapacity(), request.getDriverName());
        return ResponseEntity.status(HttpStatus.CREATED).body(bus);
    }

    @DeleteMapping("/buses/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable int id) {
        try {
            service.deleteBus(id);
            return ResponseEntity.ok(Map.of("message", "Автобус успешно удален", "id", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Не удалось удалить автобус: " + e.getMessage()));
        }
    }


    @GetMapping("/buses/available")
    public ResponseEntity<List<Bus>> getAvailableBuses() {
        return ResponseEntity.ok(service.getAvailableBuses());
    }


    @GetMapping("/buses/full")
    public ResponseEntity<List<Bus>> getFullBuses() {
        return ResponseEntity.ok(service.getFullBuses());
    }


    @GetMapping("/passengers")
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(service.getAllPassengers());
    }


    @GetMapping("/passengers/{id}")
    public ResponseEntity<?> getPassengerById(@PathVariable int id) {
        Passenger passenger = service.getAllPassengers().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

        if (passenger == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Пассажир с ID " + id + " не найден"));
        }
        return ResponseEntity.ok(passenger);
    }


    @PostMapping("/passengers")
    public ResponseEntity<Passenger> createPassenger(@RequestBody PassengerRequest request) {
        Passenger passenger = service.createPassenger(
                request.getName(),
                request.getPhoneNumber(),
                request.getDestination()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
    }


    @DeleteMapping("/passengers/{id}")
    public ResponseEntity<?> deletePassenger(@PathVariable int id) {
        try {
            service.deletePassenger(id);
            return ResponseEntity.ok(Map.of("message", "Пассажир успешно удален", "id", id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Не удалось удалить пассажира: " + e.getMessage()));
        }
    }


    @GetMapping("/passengers/withTickets")
    public ResponseEntity<List<Passenger>> getPassengersWithTickets() {
        return ResponseEntity.ok(service.getPassengersWithTickets());
    }


    @PostMapping("/tickets")
    public ResponseEntity<?> buyTicket(@RequestBody TicketRequest request) {
        try {
            boolean success = service.buyTicket(request.getPassengerId(), request.getBusId());
            if (success) {
                return ResponseEntity.ok(Map.of(
                        "message", "Билет успешно куплен",
                        "passengerId", request.getPassengerId(),
                        "busId", request.getBusId()
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("error", "Не удалось купить билет. Проверьте данные."));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }


    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBuses", service.getAllBuses().size());
        stats.put("totalPassengers", service.getAllPassengers().size());
        stats.put("totalCapacity", service.getTotalCapacity());
        stats.put("totalCurrentPassengers", service.getTotalCurrentPassengers());
        stats.put("passengersWithTickets", service.getPassengersWithTicketsCount());
        stats.put("availableBuses", service.getAvailableBuses().size());
        stats.put("fullBuses", service.getFullBuses().size());
        stats.put("availableSeats", service.getTotalCapacity() - service.getTotalCurrentPassengers());
        return ResponseEntity.ok(stats);
    }


    public static class BusRequest {
        private String routeNumber;
        private int capacity;
        private String driverName;

        public String getRouteNumber() { return routeNumber; }
        public void setRouteNumber(String routeNumber) { this.routeNumber = routeNumber; }

        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }

        public String getDriverName() { return driverName; }
        public void setDriverName(String driverName) { this.driverName = driverName; }
    }

    public static class PassengerRequest {
        private String name;
        private String phoneNumber;
        private String destination;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getPhoneNumber() { return phoneNumber; }
        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getDestination() { return destination; }
        public void setDestination(String destination) { this.destination = destination; }
    }

    public static class TicketRequest {
        private int passengerId;
        private int busId;

        public int getPassengerId() { return passengerId; }
        public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

        public int getBusId() { return busId; }
        public void setBusId(int busId) { this.busId = busId; }
    }
}

