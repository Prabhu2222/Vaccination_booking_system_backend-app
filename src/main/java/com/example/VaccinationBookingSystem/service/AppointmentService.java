package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.RequestDto.BookAppointmentRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookAppointmentResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.ENUM.AppointmentStatus;
import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import com.example.VaccinationBookingSystem.customException.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.customException.DoseAlreadyTakenException;
import com.example.VaccinationBookingSystem.customException.NoAppointmentException;
import com.example.VaccinationBookingSystem.customException.PersonNotFoundException;
import com.example.VaccinationBookingSystem.model.Appointment;
import com.example.VaccinationBookingSystem.model.Doctor;
import com.example.VaccinationBookingSystem.model.Person;
import com.example.VaccinationBookingSystem.model.VaccinationCenter;
import com.example.VaccinationBookingSystem.repository.AppointmentRepository;
import com.example.VaccinationBookingSystem.repository.DoctorRepository;
import com.example.VaccinationBookingSystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.*;


@Service
public class AppointmentService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DoctorRepository doctorRepository;
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    JavaMailSender javaMailSender;
    public BookAppointmentResponseDto bookAppointment(BookAppointmentRequestDto bookAppointmentRequestDto) {
        Optional<Person> optionalPerson=personRepository.findById(bookAppointmentRequestDto.getPersonId());
        if(optionalPerson.isEmpty()){
            throw new PersonNotFoundException("Invalid person Id");
        }
        Optional<Doctor>optionalDoctor=doctorRepository.findById(bookAppointmentRequestDto.getDoctorId());
        if(optionalDoctor.isEmpty()){
            throw new DoctorNotFoundException("Invalid Doctor Id");
        }

        Person person=optionalPerson.get();
        Doctor doctor=optionalDoctor.get();

        if(person.isDose1Taken() && bookAppointmentRequestDto.getAppointmentType().equals(AppointmentType.DOSE1))
            throw new DoseAlreadyTakenException("dose 1 already taken, schedule your appointment for second dose");
        if(person.isDose1Taken()==false && bookAppointmentRequestDto.getAppointmentType().equals(AppointmentType.DOSE2))
            throw new NoAppointmentException("First book an appointment/take dose1 and then go for dose2");
        if(person.isDose2Taken() && bookAppointmentRequestDto.getAppointmentType().equals(AppointmentType.DOSE2))
            throw new DoseAlreadyTakenException("dose 2 already taken, proceed for downloading the certificate if not downloaded yet");
        if(person.isDose2Taken() && bookAppointmentRequestDto.getAppointmentType().equals(AppointmentType.DOSE1))
            throw new DoseAlreadyTakenException("dose 2 already taken, you can't take dose1 after dose2");



        Appointment appointment=new Appointment();
        appointment.setAppointmentId(String.valueOf(UUID.randomUUID()));
        appointment.setAppointmentType(bookAppointmentRequestDto.getAppointmentType());//change in code
        appointment.setAppointmentStatus(AppointmentStatus.AVAILABLE);//change in code
        //set date manually 2 days after current date
        Date todayDate=new Date();
        long twoDaysMills=2*24*60*60*1000l;
        Date appointDate=new Date(todayDate.getTime()+twoDaysMills);
        appointDate.setHours(10);
        appointDate.setMinutes(0);
        appointDate.setSeconds(0);
        appointment.setAppointmentDate(appointDate.toLocaleString());//change in code.set appointment date 2 days after booking
        appointment.setPerson(person);
        appointment.setDoctor(doctor);
        //first save the child in the repo so that you can generate a primary key.
        Appointment savedAppointment=appointmentRepository.save(appointment);

        //now add the savedObject to the entity doctor and person
         doctor.getAppointments().add(savedAppointment);
         person.getAppointments().add(savedAppointment);
         //now save the parents
         Doctor savedDoctor=doctorRepository.save(doctor);
         Person savedPerson=personRepository.save(person);
        VaccinationCenter center=savedDoctor.getCenter();

         //send a mail
        try{
            String text="This is to inform you that you need to take COVID-19 dose because of the abrupt increase in COVID cases at many places of the world. That's why we have booked an appointment for you to take COVID dose to make you safe. Dear "+savedPerson.getName()+" Your appointment has been booked with Doctor "+
                    savedDoctor.getName()+"."+"\nYour vaccination center name is: "+center.getCenterName()+"\nYour appointment Id is: "+savedAppointment.getAppointmentId()+"."+"\nPlease reach at the " +
                    "center "+center.getAddress()+" at this time: "+savedAppointment.getAppointmentDate()+". Be vaccinated ,stay safe.\n It is to inform you that this facility is only provided in India and valid for Indian citizens only.";
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom("pojectbro@gmail.com");
            simpleMailMessage.setTo(savedPerson.getEmailId());
            simpleMailMessage.setSubject("Congrats!!! Appointment booked");
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }



          //prepare response dto
         BookAppointmentResponseDto ansResponseDto=new BookAppointmentResponseDto();


        CenterResponseDto responseDto=new CenterResponseDto();
        responseDto.setCenterName(center.getCenterName());
        responseDto.setCenterType(center.getCenterType());
        responseDto.setAddress(center.getAddress());

         ansResponseDto.setPersonName(savedPerson.getName());
         ansResponseDto.setDoctorName(savedDoctor.getName());
         ansResponseDto.setAppointmentId(savedAppointment.getAppointmentId());
         ansResponseDto.setAppointmentDate(savedAppointment.getAppointmentDate());
         ansResponseDto.setCenterResponseDto(responseDto);
         ansResponseDto.setAppointmentDate(savedAppointment.getAppointmentDate());
         ansResponseDto.setAppointmentType(savedAppointment.getAppointmentType());

         return ansResponseDto;

    }

    public List<BookAppointmentResponseDto> getDoctorAppointmentById(int doctorId) {
        Optional<Doctor> optionalDoctor=doctorRepository.findById(doctorId);
        if(optionalDoctor.isEmpty())  throw new DoctorNotFoundException("Invalid doctor Id");
        List<BookAppointmentResponseDto> list=new ArrayList<>();
        Doctor doctor =optionalDoctor.get();
        for(Appointment ele:doctor.getAppointments()){
            Person p=ele.getPerson();
            BookAppointmentResponseDto obj=new BookAppointmentResponseDto();
            obj.setPersonName(p.getName());
            obj.setDoctorName(doctor.getName());
            obj.setAppointmentId(ele.getAppointmentId());
            obj.setAppointmentDate(ele.getAppointmentDate());
            CenterResponseDto cdto=new CenterResponseDto();
            cdto.setCenterName(doctor.getCenter().getCenterName());
            cdto.setCenterType(doctor.getCenter().getCenterType());
            cdto.setAddress(doctor.getCenter().getAddress());
            obj.setCenterResponseDto(cdto);
            list.add(obj);
        }
        return list;
    }

    public List<BookAppointmentResponseDto> getPersonAppointmentById(int personId) {
        Optional<Person> optionalPerson=personRepository.findById(personId);
        if(optionalPerson.isEmpty())  throw new PersonNotFoundException("Invalid person Id");
        List<BookAppointmentResponseDto> list=new ArrayList<>();
        Person person=optionalPerson.get();
        for(Appointment ele:person.getAppointments()){
            Person p=ele.getPerson();
            BookAppointmentResponseDto obj=new BookAppointmentResponseDto();
            obj.setPersonName(p.getName());
            obj.setDoctorName(ele.getDoctor().getName());
            obj.setAppointmentId(ele.getAppointmentId());
            obj.setAppointmentDate(ele.getAppointmentDate());
            CenterResponseDto cdto=new CenterResponseDto();
            cdto.setCenterName(ele.getDoctor().getCenter().getCenterName());
            cdto.setCenterType(ele.getDoctor().getCenter().getCenterType());
            cdto.setAddress(ele.getDoctor().getCenter().getAddress());
            obj.setCenterResponseDto(cdto);
            list.add(obj);
        }
        return list;
    }

    public void cancelAppointment(String id) {
        Appointment appointment=appointmentRepository.getByAppointmentId(id);
        if(appointment==null)
            throw new NoAppointmentException("no such appointment exist");
         if(appointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED))
             throw new RuntimeException("your appointment has already completed. Invalid request");
         if(appointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED))
             throw new RuntimeException("This appointment is already cancelled");
        appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
        appointmentRepository.save(appointment);
        try{
            String text="Oops!!! "+appointment.getPerson().getName()+" Your appointment has been cancelled with Doctor "+
                    appointment.getDoctor().getName()+" at vaccination center name : "+appointment.getDoctor().getCenter().getCenterName()+" at this time: "+appointment.getAppointmentDate()+". Thank u for using our service.";
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom("pojectbro@gmail.com");
            simpleMailMessage.setTo(appointment.getPerson().getEmailId());
            simpleMailMessage.setSubject("OOPS!!! Appointment canceled");
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
