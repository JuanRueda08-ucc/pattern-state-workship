package com.barbershopqueuestate.controller;

import com.barbershopqueuestate.dto.AppointmentResponse;
import com.barbershopqueuestate.dto.CreateAppointmentRequest;
import com.barbershopqueuestate.model.Appointment;
import com.barbershopqueuestate.service.AppointmentService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody CreateAppointmentRequest request) {
        Appointment appointment = appointmentService.createAppointment(
                request.getCustomerName(),
                request.getBarberName(),
                request.getServiceName()
        );
        return ResponseEntity.ok(toResponse(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> responses = appointmentService.getAllAppointments()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(toResponse(appointment));
    }

    @PutMapping("/{id}/waiting")
    public ResponseEntity<AppointmentResponse> moveToWaiting(@PathVariable Long id) {
        Appointment appointment = appointmentService.moveToWaiting(id);
        return ResponseEntity.ok(toResponse(appointment));
    }

    @PutMapping("/{id}/start")
    public ResponseEntity<AppointmentResponse> startService(@PathVariable Long id) {
        Appointment appointment = appointmentService.startService(id);
        return ResponseEntity.ok(toResponse(appointment));
    }

    @PutMapping("/{id}/complete")
    public ResponseEntity<AppointmentResponse> completeService(@PathVariable Long id) {
        Appointment appointment = appointmentService.completeService(id);
        return ResponseEntity.ok(toResponse(appointment));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<AppointmentResponse> cancelAppointment(@PathVariable Long id) {
        Appointment appointment = appointmentService.cancelAppointment(id);
        return ResponseEntity.ok(toResponse(appointment));
    }

    private AppointmentResponse toResponse(Appointment appointment) {
        return new AppointmentResponse(appointment);
    }
}
