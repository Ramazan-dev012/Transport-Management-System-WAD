package com.example.transportsystem.service;

import com.example.transportsystem.model.User;
import com.example.transportsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(String username, String email, String password) {
        if (userRepository.existsByUsername(username)) {
            return "Username already exists!";
        }
        if (userRepository.existsByEmail(email)) {
            return "Email already registered!";
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Set.of("ROLE_USER"));
        userRepository.save(user);
        return null;
    }
}