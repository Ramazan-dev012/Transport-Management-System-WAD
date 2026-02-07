package com.example.transportsystem;

@SuppressWarnings("unused")
public class Bus {
    private int id;
    private String routeNumber;
    private int capacity;
    private String driverName;
    private int currentPassengers;

    public Bus() {
    }

    public Bus(int id, String routeNumber, int capacity, String driverName) {
        this.id = id;
        this.routeNumber = routeNumber;
        this.capacity = capacity;
        this.driverName = driverName;
        this.currentPassengers = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers = currentPassengers;
    }

    public boolean isFull() {
        return currentPassengers >= capacity;
    }

    public boolean addPassenger() {
        if (!isFull()) {
            currentPassengers++;
            return true;
        }
        return false;
    }

    public boolean removePassenger() {
        if (currentPassengers > 0) {
            currentPassengers--;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                ", routeNumber='" + routeNumber + '\'' +
                ", capacity=" + capacity +
                ", driverName='" + driverName + '\'' +
                ", currentPassengers=" + currentPassengers +
                '}';
    }
}

