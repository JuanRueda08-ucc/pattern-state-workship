package com.barbershopqueuestate.state;

import com.barbershopqueuestate.model.Appointment;

public class CompletedState implements AppointmentState {

    @Override
    public void moveToWaiting(Appointment appointment) {
        throw new IllegalStateException("A completed appointment cannot change state.");
    }

    @Override
    public void startService(Appointment appointment) {
        throw new IllegalStateException("A completed appointment cannot change state.");
    }

    @Override
    public void completeService(Appointment appointment) {
        throw new IllegalStateException("A completed appointment cannot change state.");
    }

    @Override
    public void cancel(Appointment appointment) {
        throw new IllegalStateException("A completed appointment cannot change state.");
    }

    @Override
    public String getStateName() {
        return "COMPLETED";
    }
}
