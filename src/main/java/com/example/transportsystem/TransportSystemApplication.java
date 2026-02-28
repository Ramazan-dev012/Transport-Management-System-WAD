package com.example.transportsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TransportSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransportSystemApplication.class, args);
    }
}

