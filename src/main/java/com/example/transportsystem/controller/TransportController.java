package com.example.transportsystem.controller;

import com.example.transportsystem.service.TransportManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportManagementService service;


    @GetMapping
    public String mainPage(Model model, Authentication auth) {
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        model.addAttribute("buses", service.getAllBuses());
        if (isAdmin) {
            model.addAttribute("passengers", service.getAllPassengers());
        }
        model.addAttribute("totalCapacity", service.getTotalCapacity());
        model.addAttribute("totalCurrentPassengers", service.getTotalCurrentPassengers());
        model.addAttribute("passengersWithTickets", service.getPassengersWithTicketsCount());
        return "main";
    }


    @GetMapping("/viewTickets")
    public String viewTickets(Model model) {
        model.addAttribute("availableBuses", service.getAvailableBuses());
        model.addAttribute("fullBuses", service.getFullBuses());
        model.addAttribute("passengersWithTickets", service.getPassengersWithTickets());
        model.addAttribute("allTickets", service.getAllTickets());
        model.addAttribute("availableBusesCount", service.getAvailableBuses().size());
        model.addAttribute("fullBusesCount", service.getFullBuses().size());
        model.addAttribute("ticketsCount", service.getPassengersWithTicketsCount());
        return "viewTickets";
    }


    @GetMapping("/buses")
    public String showBuses(Model model) {
        model.addAttribute("buses", service.getAllBuses());
        return "buses";
    }


    @GetMapping("/buses/add")
    public String addBusForm() {
        return "addBus";
    }


    @PostMapping("/buses/create")
    public String createBus(@RequestParam String routeNumber,
                           @RequestParam int capacity,
                           @RequestParam String driverName) {
        service.createBus(routeNumber, capacity, driverName);
        return "redirect:/transport/buses";
    }


    @PostMapping("/buses/delete")
    public String deleteBus(@RequestParam Long busId) {
        service.deleteBus(busId);
        return "redirect:/transport/buses";
    }


    @GetMapping("/passengers")
    public String showPassengers(Model model) {
        model.addAttribute("passengers", service.getAllPassengers());
        return "passengers";
    }


    @GetMapping("/passengers/add")
    public String addPassengerForm() {
        return "addPassenger";
    }


    @PostMapping("/passengers/create")
    public String createPassenger(@RequestParam String name,
                                 @RequestParam String phoneNumber,
                                 @RequestParam String destination,
                                 Authentication auth) {
        String username = (auth != null ? auth.getName() : null);

        // Если по какой-то причине auth отсутствует, создаём как раньше.
        if (username == null) {
            service.createPassenger(name, phoneNumber, destination);
        } else {
            service.createPassenger(name, phoneNumber, destination, username);
        }
        return "redirect:/transport/passengers";
    }


    @PostMapping("/passengers/delete")
    public String deletePassenger(@RequestParam Long passengerId) {
        service.deletePassenger(passengerId);
        return "redirect:/transport/passengers";
    }


    @GetMapping("/buyTicket")
    public String buyTicketForm(Model model, Authentication auth) {
        boolean isAdmin = auth != null && auth.getAuthorities().stream()
                .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

        model.addAttribute("passengers", isAdmin
                ? service.getAllPassengers()
                : service.getPassengersEligibleForPurchase(auth.getName()));
        model.addAttribute("buses", service.getAvailableBuses());
        return "buyTicket";
    }

    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam Long passengerId,
                           @RequestParam Long busId,
                           Model model,
                           Authentication auth) {
        try {
            var result = service.buyTicketAndGetDetails(passengerId, busId);
            model.addAttribute("passengerName", result.get("passengerName"));
            model.addAttribute("busRoute", result.get("busRoute"));
            model.addAttribute("copassengers", result.get("copassengers"));
            return "ticketSuccess";
        } catch (IllegalStateException e) {
            boolean isAdmin = auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_ADMIN".equals(a.getAuthority()));

            model.addAttribute("error", e.getMessage());
            model.addAttribute("passengers", isAdmin
                    ? service.getAllPassengers()
                    : service.getPassengersEligibleForPurchase(auth.getName()));
            model.addAttribute("buses", service.getAvailableBuses());
            return "buyTicket";
        }
    }
}
