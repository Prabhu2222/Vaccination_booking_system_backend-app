package com.example.VaccinationBookingSystem.model;

import com.example.VaccinationBookingSystem.ENUM.Gender;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="doctor_table")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int age;
    @Column(unique = true)
    String emailId;
    @Enumerated(EnumType.STRING)
    Gender gender;

    @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL)
    List<Appointment> appointments=new ArrayList<>();

    @ManyToOne           //relationship between vaccination center and doctor is one to many
    @JoinColumn           //Doctor is the child of Vaccination Center so pk of Vaccination Center should be created
    VaccinationCenter center;
}
