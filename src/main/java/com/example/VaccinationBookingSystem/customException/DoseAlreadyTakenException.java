package com.example.VaccinationBookingSystem.customException;

public class DoseAlreadyTakenException extends RuntimeException{
  public DoseAlreadyTakenException(String msg){
      super(msg);
  }
}
