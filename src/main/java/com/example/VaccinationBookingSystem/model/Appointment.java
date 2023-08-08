package com.example.VaccinationBookingSystem.model;

import com.example.VaccinationBookingSystem.ENUM.AppointmentStatus;
import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="appointment_table")
public class Appointment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    String appointmentId;//uuid generate
//    @CreationTimestamp//change timestamp
    String appointmentDate;

    //change start
    @Enumerated(EnumType.STRING)
    AppointmentType appointmentType;
    @Enumerated(EnumType.STRING)
    AppointmentStatus appointmentStatus;
   //change ends

    @ManyToOne //appointment to person many to one relationship
    @JoinColumn       //appointment is the child.so it must have pk of Person class
    Person person;

    @ManyToOne     //appointment to doctor is many to one
    @JoinColumn       //since appointment is child we have to create pk of doctor in this class
    Doctor doctor;


}
