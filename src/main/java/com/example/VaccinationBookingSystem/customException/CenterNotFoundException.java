package com.example.VaccinationBookingSystem.customException;

public class CenterNotFoundException extends RuntimeException{
    public CenterNotFoundException(String message){
        super(message);
    }
}
