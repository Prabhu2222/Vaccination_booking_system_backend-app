package com.example.VaccinationBookingSystem.DTO.RequestDto;

import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookAppointmentRequestDto {
    int personId;
    int doctorId;

    AppointmentType appointmentType;//change in dto attribute
}
