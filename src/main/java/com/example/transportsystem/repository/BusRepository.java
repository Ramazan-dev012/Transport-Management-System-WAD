package com.example.transportsystem.repository;

import com.example.transportsystem.model.BusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends JpaRepository<BusEntity, Long> {

    @Query("SELECT b FROM BusEntity b WHERE b.currentPassengers < b.capacity")
    List<BusEntity> findAvailableBuses();

    @Query("SELECT b FROM BusEntity b WHERE b.currentPassengers >= b.capacity")
    List<BusEntity> findFullBuses();
}

