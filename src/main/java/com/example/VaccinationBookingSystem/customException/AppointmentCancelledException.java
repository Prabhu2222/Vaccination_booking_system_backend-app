package com.example.VaccinationBookingSystem.customException;

public class AppointmentCancelledException extends RuntimeException{
    public AppointmentCancelledException(String msg){
        super(msg);
    }

}
