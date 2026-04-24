package com.example.transportsystem.factory;

import org.springframework.stereotype.Component;

import static com.example.transportsystem.factory.Strings.trimToNull;

@Component
public class DefaultTransportEntityFactory implements TransportEntityFactory {

    @Override
    public com.example.transportsystem.model.BusEntity newBus(String routeNumber, int capacity, String driverName) {
        return com.example.transportsystem.model.BusEntity.builder()
                .routeNumber(requireNonBlank(routeNumber, "routeNumber"))
                .capacity(requirePositive(capacity))
                .driverName(requireNonBlank(driverName, "driverName"))
                .currentPassengers(0)
                .build();
    }

    @Override
    public com.example.transportsystem.model.PassengerEntity newPassenger(String name, String phoneNumber, String destination) {
        return com.example.transportsystem.model.PassengerEntity.builder()
                .name(requireNonBlank(name, "name"))
                .phoneNumber(trimToNull(phoneNumber))
                .destination(trimToNull(destination))
                .hasTicket(false)
                .createdByUsername(null)
                .build();
    }

    @Override
    public com.example.transportsystem.model.TicketEntity newTicket(
            com.example.transportsystem.model.PassengerEntity passenger,
            com.example.transportsystem.model.BusEntity bus,
            int seatNumber
    ) {
        if (passenger == null) throw new IllegalArgumentException("passenger is required");
        if (bus == null) throw new IllegalArgumentException("bus is required");
        if (seatNumber <= 0) throw new IllegalArgumentException("seatNumber must be positive");

        return com.example.transportsystem.model.TicketEntity.builder()
                .passenger(passenger)
                .bus(bus)
                .seatNumber(seatNumber)
                .build();
    }

    private static String requireNonBlank(String value, String field) {
        String v = trimToNull(value);
        if (v == null) throw new IllegalArgumentException(field + " is required");
        return v;
    }

    private static int requirePositive(int value) {
        if (value <= 0) throw new IllegalArgumentException("capacity must be positive");
        return value;
    }
}
