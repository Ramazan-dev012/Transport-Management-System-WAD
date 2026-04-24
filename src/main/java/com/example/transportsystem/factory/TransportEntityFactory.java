package com.example.transportsystem.factory;

/**
 * Factory for creating domain entities.
 *
 * Purpose: centralize object creation + basic normalization/validation.
 */
public interface TransportEntityFactory {

    com.example.transportsystem.model.BusEntity newBus(String routeNumber, int capacity, String driverName);

    com.example.transportsystem.model.PassengerEntity newPassenger(String name, String phoneNumber, String destination);

    com.example.transportsystem.model.TicketEntity newTicket(
            com.example.transportsystem.model.PassengerEntity passenger,
            com.example.transportsystem.model.BusEntity bus,
            int seatNumber
    );
}

