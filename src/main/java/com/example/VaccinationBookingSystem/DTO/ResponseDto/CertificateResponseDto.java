package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CertificateResponseDto {
    String personName;
    int age;
    Gender gender;
    String certificateNo;
    String message;
    String Dose1Date;
    String Dose2Date;
}
