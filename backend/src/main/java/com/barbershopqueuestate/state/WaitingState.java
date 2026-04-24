package com.barbershopqueuestate.state;

import com.barbershopqueuestate.model.Appointment;

public class WaitingState implements AppointmentState {

    @Override
    public void moveToWaiting(Appointment appointment) {
        throw new IllegalStateException("A waiting appointment is already in waiting state.");
    }

    @Override
    public void startService(Appointment appointment) {
        appointment.changeState(new InServiceState());
    }

    @Override
    public void completeService(Appointment appointment) {
        throw new IllegalStateException("A waiting appointment cannot be completed before service starts.");
    }

    @Override
    public void cancel(Appointment appointment) {
        appointment.changeState(new CancelledState());
    }

    @Override
    public String getStateName() {
        return "WAITING";
    }
}
