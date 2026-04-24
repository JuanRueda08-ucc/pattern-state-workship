package com.barbershopqueuestate.model;

import com.barbershopqueuestate.state.AppointmentState;
import com.barbershopqueuestate.state.RegisteredState;
import java.time.LocalDateTime;

public class Appointment {

    private Long id;
    private String customerName;
    private String barberName;
    private String serviceName;
    private AppointmentState currentState;
    private LocalDateTime creationDate;

    public Appointment() {
        this.currentState = new RegisteredState();
        this.creationDate = LocalDateTime.now();
    }

    public Appointment(Long id, String customerName, String barberName, String serviceName) {
        this.id = id;
        this.customerName = customerName;
        this.barberName = barberName;
        this.serviceName = serviceName;
        this.currentState = new RegisteredState();
        this.creationDate = LocalDateTime.now();
    }

    public void moveToWaiting() {
        currentState.moveToWaiting(this);
    }

    public void startService() {
        currentState.startService(this);
    }

    public void completeService() {
        currentState.completeService(this);
    }

    public void cancel() {
        currentState.cancel(this);
    }

    public void changeState(AppointmentState newState) {
        if (newState == null) {
            throw new IllegalArgumentException("New state cannot be null.");
        }
        this.currentState = newState;
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getBarberName() {
        return barberName;
    }

    public void setBarberName(String barberName) {
        this.barberName = barberName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
