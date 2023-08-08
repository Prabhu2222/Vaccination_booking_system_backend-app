package com.example.VaccinationBookingSystem.model;

import com.example.VaccinationBookingSystem.ENUM.DoseType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="dose_table")
public class Dose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String doseId;//uuid generated
    @Enumerated(value=EnumType.STRING)
    DoseType doseType;

//    @CreationTimestamp//change date according to appointment
    String doseDate;
    @ManyToOne           //declaring dose to person is many to one i.e from current class to connecting class cardinality
    @JoinColumn         //Introduces the foreignKey column and by default it joins based on primaryKey of the parent
    Person person;

    //there is a difference between util date vs sql date .util date has both date and time while sql date has only date.
}
