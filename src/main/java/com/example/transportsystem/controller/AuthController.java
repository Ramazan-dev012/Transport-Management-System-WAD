package com.example.transportsystem.controller;

import com.example.transportsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String registered,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Неверный логин или пароль. Попробуйте снова.");
        }
        if (registered != null) {
            model.addAttribute("successMessage", "Регистрация прошла успешно! Теперь войдите.");
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
        if (username.trim().length() < 3) {
            model.addAttribute("errorMessage", "Имя пользователя должно быть не менее 3 символов!");
            return "auth/register";
        }
        if (password.length() < 6) {
            model.addAttribute("errorMessage", "Пароль должен быть не менее 6 символов!");
            return "auth/register";
        }
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Пароли не совпадают!");
            return "auth/register";
        }

        String error = authService.registerUser(username.trim(), email.trim(), password);
        if (error != null) {
            model.addAttribute("errorMessage", error);
            return "auth/register";
        }

        return "redirect:/auth/login?registered=true";
    }
}
