package com.example.transportsystem.controller;

import com.example.transportsystem.dto.RegisterRequest;
import com.example.transportsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * MVP REST API: регистрация.
 * Логин уже есть через Spring Security formLogin (/auth/login).
 */
@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    private final AuthService authService;

    public ApiAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        authService.registerUser(req.getUsername(), req.getEmail(), req.getPassword(), req.getConfirmPassword());
        return ResponseEntity.ok("Success");
    }
}

