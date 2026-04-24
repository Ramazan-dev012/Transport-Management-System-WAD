package com.example.transportsystem.service;

import com.example.transportsystem.model.User;
import com.example.transportsystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired(required = false)
    private EmailService emailService;

    /**
     * Бизнес-логика регистрации (сервисный слой):
     * - пользователь уникальный
     * - email уникальный
     * - пароль минимум 6 символов (также валидируется аннотациями/DTO)
     */
    public void registerUser(String username, String email, String password, String confirmPassword) {
        String u = username == null ? null : username.trim();
        String e = email == null ? null : email.trim();

        if (u == null || u.isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (u.length() < 3) {
            throw new IllegalArgumentException("username must be at least 3 characters");
        }

        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }
        if (password.length() < 6) {
            throw new IllegalArgumentException("password must be at least 6 characters");
        }
        if (confirmPassword == null || !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("passwords do not match");
        }

        if (e == null || e.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }

        if (userRepository.existsByUsername(u)) {
            throw new IllegalStateException("User already exists");
        }
        if (userRepository.existsByEmail(e)) {
            throw new IllegalStateException("Email already registered");
        }

        User user = new User();
        user.setUsername(u);
        user.setEmail(e);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("ROLE_USER"));
        userRepository.save(user);

        // Практика ЛР-10: письмо при регистрации
        if (emailService != null) {
            try {
                emailService.sendEmail(
                        user.getEmail(),
                        "Регистрация в Transport Management System",
                        "Здравствуйте, " + user.getUsername() + "!\n\n" +
                                "Вы успешно зарегистрировались в Transport Management System.\n" +
                                "Теперь вы можете войти в систему и покупать билеты.\n\n" +
                                "Если это были не вы — проигнорируйте это письмо."
                );
            } catch (Exception e1) {
                // Не ломаем регистрацию из-за проблем SMTP
                log.warn("Не удалось отправить email при регистрации. user={}, email={}, причина={} : {}",
                        user.getUsername(), user.getEmail(), e1.getClass().getSimpleName(), e1.getMessage());
            }
        }
    }
}