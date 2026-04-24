package com.example.transportsystem.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Диагностика SMTP-конфига (без раскрытия пароля).
 * Открыто, потому что проект учебный; в реальном проекте нужно закрыть доступ.
 */
@RestController
@RequestMapping("/email")
public class MailDebugController {

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.port:0}")
    private int port;

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    @GetMapping("/config")
    public ResponseEntity<Map<String, Object>> config() {
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("spring.profiles.active", activeProfiles);
        out.put("spring.mail.host", host);
        out.put("spring.mail.port", port);
        out.put("spring.mail.username", username);
        out.put("spring.mail.password.set", password != null && !password.isBlank());
        return ResponseEntity.ok(out);
    }
}

