package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GeneralDoctorResponseDto {
    String name;
    int age;

    String emailId;

    Gender gender;
    int noOfAppointments;
}
