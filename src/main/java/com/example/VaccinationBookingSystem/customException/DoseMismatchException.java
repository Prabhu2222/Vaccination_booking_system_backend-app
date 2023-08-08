package com.example.VaccinationBookingSystem.customException;

public class DoseMismatchException extends RuntimeException{
    public DoseMismatchException(String message){
        super(message);
    }
}
