package com.example.transportsystem.controller;

import com.example.transportsystem.Bus;
import com.example.transportsystem.Passenger;
import com.example.transportsystem.service.TransportManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/transport")
public class TransportController {

    @Autowired
    private TransportManagementService service;


    @GetMapping
    public String mainPage(Model model) {
        model.addAttribute("buses", service.getAllBuses());
        model.addAttribute("passengers", service.getAllPassengers());
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
    public String deleteBus(@RequestParam int busId) {
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
                                 @RequestParam String destination) {
        service.createPassenger(name, phoneNumber, destination);
        return "redirect:/transport/passengers";
    }


    @PostMapping("/passengers/delete")
    public String deletePassenger(@RequestParam int passengerId) {
        service.deletePassenger(passengerId);
        return "redirect:/transport/passengers";
    }


    @GetMapping("/buyTicket")
    public String buyTicketForm(Model model) {
        model.addAttribute("passengers", service.getAllPassengers());
        model.addAttribute("buses", service.getAllBuses());
        return "buyTicket";
    }


    @PostMapping("/buyTicket")
    public String buyTicket(@RequestParam int passengerId,
                           @RequestParam int busId) {
        service.buyTicket(passengerId, busId);
        return "redirect:/transport";
    }
}

