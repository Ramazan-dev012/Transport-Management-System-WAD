package com.example.transportsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntityBuilderTest {

    @Test
    void busBuilder_buildsEntity() {
        BusEntity bus = BusEntity.builder()
                .routeNumber("10")
                .capacity(30)
                .driverName("Bob")
                .currentPassengers(2)
                .build();

        assertEquals("10", bus.getRouteNumber());
        assertEquals(30, bus.getCapacity());
        assertEquals("Bob", bus.getDriverName());
        assertEquals(2, bus.getCurrentPassengers());
    }

    @Test
    void passengerBuilder_buildsEntity() {
        PassengerEntity p = PassengerEntity.builder()
                .name("Alice")
                .phoneNumber("123")
                .destination("Center")
                .hasTicket(true)
                .build();

        assertEquals("Alice", p.getName());
        assertTrue(p.isHasTicket());
    }
}
