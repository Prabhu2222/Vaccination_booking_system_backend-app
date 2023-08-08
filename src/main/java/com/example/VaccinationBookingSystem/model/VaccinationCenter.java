package com.example.VaccinationBookingSystem.model;

import com.example.VaccinationBookingSystem.ENUM.CenterType;
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
@Table(name="vaccinationCenter_table")
public class VaccinationCenter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String centerName;
    @Enumerated(value=EnumType.STRING)
    CenterType centerType;
    String address;
    @OneToMany(mappedBy = "center",cascade = CascadeType.ALL)
    List<Doctor> doctors=new ArrayList<>();
}
