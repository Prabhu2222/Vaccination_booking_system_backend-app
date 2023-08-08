package com.example.VaccinationBookingSystem.DTO.ResponseDto;

import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookAppointmentResponseDto {
    String personName;
    String doctorName;
    String appointmentId;
    String appointmentDate;
    AppointmentType appointmentType;
    CenterResponseDto centerResponseDto;
}
