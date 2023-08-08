package com.example.VaccinationBookingSystem.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level= AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="certificate_table")
public class Certificate {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;
    String certificateNo;
    String confirmationMessage;

    @OneToOne          //from certificate to person relationship is one to one
    @JoinColumn        //since certifiacate is the child it should contain pk of parent class which is person
    Person person;


}
