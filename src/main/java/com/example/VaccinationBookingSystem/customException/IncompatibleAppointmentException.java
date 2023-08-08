package com.example.VaccinationBookingSystem.customException;

public class IncompatibleAppointmentException extends RuntimeException{
    public IncompatibleAppointmentException(String msg){
        super(msg);
    }
}
