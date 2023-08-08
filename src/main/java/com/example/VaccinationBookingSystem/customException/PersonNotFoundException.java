package com.example.VaccinationBookingSystem.customException;

public class PersonNotFoundException extends RuntimeException{
    public PersonNotFoundException(String msg){
        super(msg);
    }
}
