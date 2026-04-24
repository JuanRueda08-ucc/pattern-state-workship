package com.barbershopqueuestate.service;

import com.barbershopqueuestate.model.Appointment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final Map<Long, Appointment> appointments = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Appointment createAppointment(String customerName, String barberName, String serviceName) {
        Long id = idGenerator.getAndIncrement();
        Appointment appointment = new Appointment(id, customerName, barberName, serviceName);
        appointments.put(id, appointment);
        return appointment;
    }

    public List<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public Appointment getAppointmentById(Long id) {
        return findAppointmentById(id);
    }

    public Appointment moveToWaiting(Long id) {
        Appointment appointment = findAppointmentById(id);
        appointment.moveToWaiting();
        return appointment;
    }

    public Appointment startService(Long id) {
        Appointment appointment = findAppointmentById(id);
        appointment.startService();
        return appointment;
    }

    public Appointment completeService(Long id) {
        Appointment appointment = findAppointmentById(id);
        appointment.completeService();
        return appointment;
    }

    public Appointment cancelAppointment(Long id) {
        Appointment appointment = findAppointmentById(id);
        appointment.cancel();
        return appointment;
    }

    private Appointment findAppointmentById(Long id) {
        Appointment appointment = appointments.get(id);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment not found with id: " + id);
        }
        return appointment;
    }
}
