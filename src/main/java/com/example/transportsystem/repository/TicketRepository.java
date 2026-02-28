package com.example.transportsystem.repository;

import com.example.transportsystem.model.BusEntity;
import com.example.transportsystem.model.PassengerEntity;
import com.example.transportsystem.model.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    List<TicketEntity> findByPassenger(PassengerEntity passenger);
    List<TicketEntity> findByBus(BusEntity bus);
    Optional<TicketEntity> findByPassengerAndBus(PassengerEntity passenger, BusEntity bus);
    boolean existsByPassengerAndBus(PassengerEntity passenger, BusEntity bus);
}

