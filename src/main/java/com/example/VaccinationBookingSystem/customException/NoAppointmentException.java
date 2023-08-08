package com.example.VaccinationBookingSystem.customException;

public class NoAppointmentException extends RuntimeException{
    public NoAppointmentException(String msg){
        super(msg);
    }
}
