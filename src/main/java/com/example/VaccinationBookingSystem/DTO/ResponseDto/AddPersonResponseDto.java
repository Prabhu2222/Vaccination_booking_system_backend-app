package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPersonResponseDto {
    String name;
    String message;
}
