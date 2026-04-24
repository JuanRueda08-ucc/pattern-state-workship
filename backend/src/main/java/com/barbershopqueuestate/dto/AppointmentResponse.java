package com.barbershopqueuestate.dto;

import com.barbershopqueuestate.model.Appointment;
import java.time.LocalDateTime;

public class AppointmentResponse {

    private Long id;
    private String customerName;
    private String barberName;
    private String serviceName;
    private String currentState;
    private LocalDateTime creationDate;

    public AppointmentResponse() {
    }

    public AppointmentResponse(Appointment appointment) {
        this.id = appointment.getId();
        this.customerName = appointment.getCustomerName();
        this.barberName = appointment.getBarberName();
        this.serviceName = appointment.getServiceName();
        this.currentState = appointment.getCurrentStateName();
        this.creationDate = appointment.getCreationDate();
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

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
