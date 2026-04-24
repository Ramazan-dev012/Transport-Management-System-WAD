package com.example.transportsystem.repository;

import com.example.transportsystem.model.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
    List<PassengerEntity> findByHasTicketTrue();
    List<PassengerEntity> findByHasTicketFalse();

    List<PassengerEntity> findByCreatedByUsernameOrderByIdDesc(String createdByUsername);
    List<PassengerEntity> findByCreatedByUsernameAndHasTicketFalseOrderByIdDesc(String createdByUsername);
}
