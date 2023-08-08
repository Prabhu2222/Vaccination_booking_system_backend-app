package com.example.VaccinationBookingSystem.DTO.RequestDto;

import com.example.VaccinationBookingSystem.ENUM.CenterType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CenterRequestDto {
    String centerName;
    CenterType centerType;
    String address;
}
