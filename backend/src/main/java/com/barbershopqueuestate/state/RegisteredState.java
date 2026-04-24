package com.barbershopqueuestate.state;

import com.barbershopqueuestate.model.Appointment;

public class RegisteredState implements AppointmentState {

    @Override
    public void moveToWaiting(Appointment appointment) {
        appointment.changeState(new WaitingState());
    }

    @Override
    public void startService(Appointment appointment) {
        throw new IllegalStateException("A registered appointment cannot start service directly.");
    }

    @Override
    public void completeService(Appointment appointment) {
        throw new IllegalStateException("A registered appointment cannot be completed.");
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.changeState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "REGISTERED";
    }
}
