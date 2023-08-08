package com.example.VaccinationBookingSystem.DateTesting;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class DateClassUse {
    public static void main(String[] args) {
        Date date=new Date();
        System.out.println(date.getDate());
        System.out.println(date.getMonth());
        System.out.println(date.getYear());
        System.out.println(date.toString());
        System.out.println(date.getTime());

        long twoDaysMillis=2*24*60*60*1000l;
        Date twoDaysLater=new Date(date.getTime()+twoDaysMillis);
        twoDaysLater.setHours(9);
        twoDaysLater.setMinutes(0);
        twoDaysLater.setSeconds(0);
        System.out.println(twoDaysLater.toString());
        String dateArr[]=twoDaysLater.toLocaleString().split(",");
        for(String ele:dateArr)
            System.out.print(ele+" ");






    }
}
