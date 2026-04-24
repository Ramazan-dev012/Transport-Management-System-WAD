package com.example.transportsystem.controller;

import com.example.transportsystem.model.User;
import com.example.transportsystem.repository.UserRepository;
import com.example.transportsystem.service.AuthService;
import com.example.transportsystem.service.TransportManagementService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private TransportManagementService transportService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String registered,
                            @RequestParam(required = false) String expired,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль. Попробуйте снова.");
        }
        if (registered != null) {
            model.addAttribute("successMessage", "Регистрация прошла успешно! Теперь войдите.");
        }
        if (expired != null) {
            model.addAttribute("expiredMessage", "Сессия истекла (30 сек неактивности). Войдите снова.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        try {
            authService.registerUser(username, email, password, confirmPassword);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "auth/register";
        }

        return "redirect:/auth/login?registered=true";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication auth, HttpSession session) {
        String username = auth.getName();
        model.addAttribute("username", username);

        Optional<User> userOpt = userRepository.findByUsername(username);
        userOpt.ifPresent(user -> {
            model.addAttribute("email", user.getEmail());
            model.addAttribute("roles", user.getRoles());
        });

        model.addAttribute("myPassengers", transportService.getPassengersByOwner(username));

        model.addAttribute("sessionId", session.getId());
        model.addAttribute("sessionCreated",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        model.addAttribute("authorities", auth.getAuthorities());

        return "auth/profile";
    }
}
