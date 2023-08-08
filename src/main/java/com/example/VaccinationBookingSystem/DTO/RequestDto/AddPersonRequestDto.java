package com.example.VaccinationBookingSystem.DTO.RequestDto;

import com.example.VaccinationBookingSystem.ENUM.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPersonRequestDto {
    String name;
    int age;
    String emailId;
    Gender gender;
}
