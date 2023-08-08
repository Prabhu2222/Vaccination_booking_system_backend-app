package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GetFemalesWithDose1ResponseDto {
    String personName;
    DoseType doseType;
}
