package com.barbershopqueuestate.state;

import com.barbershopqueuestate.model.Appointment;

public interface AppointmentState {

    void moveToWaiting(Appointment appointment);

    void startService(Appointment appointment);

    void completeService(Appointment appointment);

    void cancel(Appointment appointment);

    String getStateName();
}
