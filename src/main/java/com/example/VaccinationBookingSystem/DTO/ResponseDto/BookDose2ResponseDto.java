package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.DoseType;
import com.example.VaccinationBookingSystem.model.Person;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDose2ResponseDto {
    String doseId;
    DoseType doseType;
    String doseDate;
    String personName;

    @Override
    public String toString() {
        return "Congratulations!!! " +
                personName +". You Have  taken the 2nd dose of "+
                doseType  +" on : "+ doseDate +
                "\nyour 2nd dose Id is: "+doseId+
                "\n Please make sure you download" +
                " the certificate.";

    }
}
