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
@Table(name="person_table")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String name;
    int age;
    @Column(unique = true)
    String emailId;
    @Enumerated(EnumType.STRING)
    Gender gender;
    boolean Dose1Taken;
    boolean Dose2Taken;
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    List<Dose> doseTaken=new ArrayList<>();
    @OneToOne(mappedBy = "person",cascade =CascadeType.ALL)
    Certificate certificate;
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)                    //relationship between person and appointment is one to one & person is the parent
    List<Appointment> appointments=new ArrayList<>();        //so use list of appointments.
}
