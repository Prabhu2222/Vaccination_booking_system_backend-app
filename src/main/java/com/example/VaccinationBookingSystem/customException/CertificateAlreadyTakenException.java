package com.example.VaccinationBookingSystem.customException;

public class CertificateAlreadyTakenException extends RuntimeException{
    public CertificateAlreadyTakenException(String message){
        super(message);
    }
}
