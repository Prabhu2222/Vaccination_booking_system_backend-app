package com.example.VaccinationBookingSystem.customException;

public class SecondDoseNotTakenException extends RuntimeException{
    public SecondDoseNotTakenException(String message){
        super(message);
    }
}
