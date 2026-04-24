package com.barbershopqueuestate.state;

import com.barbershopqueuestate.model.Appointment;

public class InServiceState implements AppointmentState {

    @Override
    public void moveToWaiting(Appointment appointment) {
        throw new IllegalStateException("An in-service appointment cannot return to waiting.");
    }

    @Override
    public void startService(Appointment appointment) {
        throw new IllegalStateException("An in-service appointment has already started.");
    }

    @Override
    public void completeService(Appointment appointment) {
        appointment.changeState(new CompletedState());
    }

    @Override
    public void cancel(Appointment appointment) {
        throw new IllegalStateException("An in-service appointment cannot be cancelled.");
    }

    @Override
    public String getStateName() {
        return "IN_SERVICE";
    }
}
