package com.barbershopqueuestate.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateAppointmentRequest {

    @NotBlank(message = "Customer name is required.")
    private String customerName;
    @NotBlank(message = "Barber name is required.")
    private String barberName;
    @NotBlank(message = "Service name is required.")
    private String serviceName;

    public CreateAppointmentRequest() {
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
}
