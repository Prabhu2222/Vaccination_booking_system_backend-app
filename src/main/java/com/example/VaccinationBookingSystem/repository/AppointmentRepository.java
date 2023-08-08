package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
   @Query(value="select * from appointment_table where appointment_id=:id",nativeQuery = true)
    Appointment getByAppointmentId(String id);
}
