package com.example.transportsystem.config;

import com.example.transportsystem.model.BusEntity;
import com.example.transportsystem.model.PassengerEntity;
import com.example.transportsystem.repository.BusRepository;
import com.example.transportsystem.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public void run(String... args) throws Exception {

        if (busRepository.count() == 0) {
            busRepository.save(new BusEntity("101", 40, "Иван Петров"));
            busRepository.save(new BusEntity("202", 50, "Мария Сидорова"));
            busRepository.save(new BusEntity("303", 30, "Сергей Иванов"));
            busRepository.save(new BusEntity("404", 45, "Анна Козлова"));
            System.out.println("[DataInitializer] Автобусы добавлены в PostgreSQL.");
        }
        if (passengerRepository.count() == 0) {
            passengerRepository.save(new PassengerEntity("Алексей Смирнов", "+77001234567", "Центр"));
            passengerRepository.save(new PassengerEntity("Джон Доу", "+77009876543", "Аэропорт"));
            System.out.println("[DataInitializer] Пассажиры добавлены в PostgreSQL.");
        }
    }
}

