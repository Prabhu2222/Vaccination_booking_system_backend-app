package com.example.VaccinationBookingSystem.customException;

public class DoctorNotFoundException extends RuntimeException{
    public DoctorNotFoundException(String msg){
        super(msg);
    }


}
