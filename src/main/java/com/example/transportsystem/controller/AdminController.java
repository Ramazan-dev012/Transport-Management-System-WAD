package com.example.transportsystem.controller;

import com.example.transportsystem.service.TransportManagementService;
import com.example.transportsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransportManagementService transportManagementService;

    @GetMapping("/users")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/{id}/roles")
    public String updateUserRoles(@PathVariable Long id, @RequestParam Set<String> roles) {
        userService.updateUserRoles(id, roles);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/tickets")
    public String adminTickets(Model model) {
        model.addAttribute("availableBuses", transportManagementService.getAvailableBuses());
        model.addAttribute("fullBuses", transportManagementService.getFullBuses());
        model.addAttribute("passengersWithTickets", transportManagementService.getPassengersWithTickets());
        model.addAttribute("allTickets", transportManagementService.getAllTickets());
        model.addAttribute("availableBusesCount", transportManagementService.getAvailableBuses().size());
        model.addAttribute("fullBusesCount", transportManagementService.getFullBuses().size());
        model.addAttribute("ticketsCount", transportManagementService.getPassengersWithTicketsCount());
        return "admin/tickets";
    }
}
