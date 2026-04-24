package com.example.transportsystem.service;

import com.example.transportsystem.model.User;
import com.example.transportsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void updateUserRoles(Long userId, Set<String> roles) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setRoles(roles);
            userRepository.save(user);
        });
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}

