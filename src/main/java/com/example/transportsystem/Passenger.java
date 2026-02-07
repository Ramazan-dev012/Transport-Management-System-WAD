package com.example.transportsystem;

@SuppressWarnings("unused")
public class Passenger {
    private int id;
    private String name;
    private String phoneNumber;
    private String destination;
    private boolean hasTicket;

    public Passenger() {
    }

    public Passenger(int id, String name, String phoneNumber, String destination) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.destination = destination;
        this.hasTicket = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isHasTicket() {
        return hasTicket;
    }

    public void setHasTicket(boolean hasTicket) {
        this.hasTicket = hasTicket;
    }

    public void buyTicket() {
        this.hasTicket = true;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", hasTicket=" + hasTicket +
                '}';
    }
}

