package com.example.VaccinationBookingSystem.DTO.RequestDto;

import com.example.VaccinationBookingSystem.ENUM.DoseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookDose2RequestDto {
    int personId;
    DoseType doseType;
}
