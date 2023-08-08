package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.ResponseDto.CertificateResponseDto;
import com.example.VaccinationBookingSystem.ENUM.AppointmentStatus;
import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import com.example.VaccinationBookingSystem.customException.CertificateAlreadyTakenException;
import com.example.VaccinationBookingSystem.customException.FirstDoseNotTakenException;
import com.example.VaccinationBookingSystem.customException.PersonNotFoundException;
import com.example.VaccinationBookingSystem.customException.SecondDoseNotTakenException;
import com.example.VaccinationBookingSystem.model.Appointment;
import com.example.VaccinationBookingSystem.model.Certificate;
import com.example.VaccinationBookingSystem.model.Dose;
import com.example.VaccinationBookingSystem.model.Person;
import com.example.VaccinationBookingSystem.repository.CertificateRepository;
import com.example.VaccinationBookingSystem.repository.PersonRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

@Service
public class CertificateService {
    @Autowired
    CertificateRepository certificateRepository;
    @Autowired
    PersonRepository personRepository;



    public CertificateResponseDto getCertificate(int personId) {
        Optional<Person> optionalPerson=personRepository.findById(personId);
        if(optionalPerson.isEmpty()) throw new PersonNotFoundException("Invalid id");
        Person person=optionalPerson.get();
        if(!person.isDose1Taken()) throw new FirstDoseNotTakenException("First dose not taken");
        if(!person.isDose2Taken())throw new SecondDoseNotTakenException("Second dose not taken");
        if(person.getCertificate()!=null) throw new CertificateAlreadyTakenException("you have already taken the certificate");

        Certificate certificate=new Certificate();
        certificate.setCertificateNo(String.valueOf(UUID.randomUUID()));
        certificate.setConfirmationMessage("Congo!!! Your certificate confirmed");
        certificate.setPerson(person);

        person.setCertificate(certificate);

        Person savedPerson=personRepository.save(person);
        CertificateResponseDto response=new CertificateResponseDto();
        response.setPersonName(savedPerson.getName());
        response.setAge(savedPerson.getAge());
        response.setGender(savedPerson.getGender());
        response.setCertificateNo(certificate.getCertificateNo());
        for(Appointment appointment:savedPerson.getAppointments()){
            if(appointment.getAppointmentType().equals(AppointmentType.DOSE1) && appointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED))
                response.setDose1Date(appointment.getAppointmentDate());
            if(appointment.getAppointmentType().equals(AppointmentType.DOSE2) && appointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED))
                response.setDose2Date(appointment.getAppointmentDate());
        }
        response.setMessage("Congratulation!!! you can now download the certificate");

        return response;


    }
}
