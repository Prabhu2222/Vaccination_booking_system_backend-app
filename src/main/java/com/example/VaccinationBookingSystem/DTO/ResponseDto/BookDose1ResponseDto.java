package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDose1ResponseDto {
    String doseId;
    DoseType doseType;
    String doseDate;
    String personName;

    @Override
    public String toString() {
        return "Congratulations!!! " +
                personName +". You Have taken"+
                doseType +"as First dose" +" on : "+ doseDate +
                "\nyour 1st dose Id is: "+doseId+
                "\n please make sure you take" +
                " second dose of that type only.";

    }
}
