package com.example.VaccinationBookingSystem.customException;

public class FirstDoseNotTakenException extends RuntimeException{
    public FirstDoseNotTakenException(String message){
        super(message);
    }
}
