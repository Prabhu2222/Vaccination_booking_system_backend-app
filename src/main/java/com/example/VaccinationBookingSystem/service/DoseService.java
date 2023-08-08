package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.RequestDto.BookDose1RequestDto;
import com.example.VaccinationBookingSystem.DTO.RequestDto.BookDose2RequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookDose1ResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookDose2ResponseDto;
import com.example.VaccinationBookingSystem.ENUM.AppointmentStatus;
import com.example.VaccinationBookingSystem.ENUM.AppointmentType;
import com.example.VaccinationBookingSystem.ENUM.DoseType;
import com.example.VaccinationBookingSystem.customException.*;
import com.example.VaccinationBookingSystem.model.Appointment;
import com.example.VaccinationBookingSystem.model.Dose;
import com.example.VaccinationBookingSystem.model.Person;
import com.example.VaccinationBookingSystem.repository.DoseRepository;
import com.example.VaccinationBookingSystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DoseService {
    @Autowired
    DoseRepository doseRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    JavaMailSender javaMailSender;

//    public Dose getDose1(int personId, DoseType doseType) {
//
//        //first check whether personId is valid or not or else throw exception
//        Optional<Person> optionalPerson=personRepository.findById(personId);
//        if(!optionalPerson.isPresent()) throw new PersonNotFoundException("Invalid PersonId");
//        //check whether dose1 is already taken or not
//        Person personObj=optionalPerson.get();
//        if(personObj.isDose1Taken()) throw new DoseAlreadyTakenException("dose1 already taken");
//
//        //create Dose object and give it
//        Dose dose=new Dose();
//        dose.setDoseId(String.valueOf(UUID.randomUUID()));
//        dose.setDoseType(doseType);
//        dose.setPerson(personObj);
//
//        //make changes in the person class making isdose1tkaen to be true
//        personObj.setDose1Taken(true);
//        personObj.getDoseTaken().add(dose);//changes for bidirectional
//        //v.v.v.v.Important-save the personObj to database else changes will not be saved;
//        personRepository.save(personObj);
//
//        //in dose object also set the person and return this dose object
////        return doseRepository.save(dose);//here in bidirectional we dont need dose repository.
//        return dose;//here it is not correct coz we are not returinng a saved entity in the table
//
//    }
public BookDose1ResponseDto getDose1(BookDose1RequestDto bookDose1RequestDto) {

    //first check whether personId is valid or not or else throw exception
    Optional<Person> optionalPerson=personRepository.findById(bookDose1RequestDto.getPersonId());
    if(!optionalPerson.isPresent()) throw new PersonNotFoundException("Invalid PersonId");
    //check whether dose1 is already taken or not
    Person personObj=optionalPerson.get();
    if(personObj.isDose1Taken()) throw new DoseAlreadyTakenException("dose1 already taken");
    //change of code .check the latest appointmentType and status of that person
    List<Appointment> list= personObj.getAppointments();
    if(list.size()==0) throw new NoAppointmentException("you have not booked an appointment for this dose1");
    Appointment lastAppointment=list.get(list.size()-1);
    if(lastAppointment.getAppointmentType().equals(AppointmentType.DOSE1) && lastAppointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED)){
        throw new AppointmentCancelledException("you have cancelled the appointment for this dose1");
    }
   

    //create Dose object and give it
    Dose dose=new Dose();
    dose.setDoseId(String.valueOf(UUID.randomUUID()));
    dose.setDoseType(bookDose1RequestDto.getDoseType());
    List<Appointment> lists=personObj.getAppointments();//change of code
    Appointment appointmentLast=list.get(list.size()-1);//change of code
    appointmentLast.setAppointmentStatus(AppointmentStatus.COMPLETED);//change of code
    dose.setDoseDate(appointmentLast.getAppointmentDate());//change of code
    
    dose.setPerson(personObj);

    //make changes in the person class making isdose1tkaen to be true
    personObj.setDose1Taken(true);
    personObj.getDoseTaken().add(dose);//changes for bidirectional
    //v.v.v.v.Important-save the personObj to database else changes will not be saved;
    Person savedPerson=personRepository.save(personObj);
    Dose savedDose=savedPerson.getDoseTaken().get(savedPerson.getDoseTaken().size()-1);
    try{
        String text="Congrats!!! "+savedPerson.getName()+". You have taken Dose1. Please make sure you take second dose of" +
                "same type.\n Be Healthy.Be Happy.";
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setSubject("Dose1");
        simpleMailMessage.setFrom("pojectbro@gmail.com");
        simpleMailMessage.setTo(savedPerson.getEmailId());
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }catch (Exception e){
        System.out.println(e.getMessage());
    }



    //in dose object also set the person and return this dose object
//        return doseRepository.save(dose);//here in bidirectional we dont need dose repository.
//    return dose;//here it is not correct coz we are not returinng a saved entity in the table
    BookDose1ResponseDto responseDto=new BookDose1ResponseDto();
    responseDto.setDoseId(savedDose.getDoseId());
    responseDto.setDoseType(savedDose.getDoseType());
    responseDto.setDoseDate(savedDose.getDoseDate());
    responseDto.setPersonName(savedPerson.getName());



    return responseDto;

}

    public BookDose2ResponseDto getDose2(BookDose2RequestDto bookDose2RequestDto) {
    //check person exist or not
        boolean eligible=false;
        Optional<Person> optionalPerson=personRepository.findById(bookDose2RequestDto.getPersonId());
        if(optionalPerson.isEmpty()) throw new PersonNotFoundException("Invalid Id");
        if(optionalPerson.get().isDose2Taken()) throw new DoseAlreadyTakenException("dose 2 already taken");
        if(!optionalPerson.get().isDose1Taken()) throw new FirstDoseNotTakenException("First take Dose 1.then you can take dose 2.");
        if(optionalPerson.get().isDose1Taken()){
            Dose saveddose=optionalPerson.get().getDoseTaken().get(0);
            if(saveddose.getDoseType().equals(bookDose2RequestDto.getDoseType())){
                eligible=true;
            }else throw new DoseMismatchException("dose2 type dose not match with dose1 type.");
        }
        //check appointment for dose2
        Person person=optionalPerson.get();
        //change of code .check the latest appointmentType and status of that person
        List<Appointment> list= person.getAppointments();
        Appointment lastAppointment=list.get(list.size()-1);
        if(lastAppointment.getAppointmentType().equals(AppointmentType.DOSE2) && lastAppointment.getAppointmentStatus().equals(AppointmentStatus.CANCELED)){
            throw new AppointmentCancelledException("you have cancelled the appointment for this dose2");
        }if(lastAppointment.getAppointmentType().equals(AppointmentType.DOSE1) && lastAppointment.getAppointmentStatus().equals(AppointmentStatus.COMPLETED))
            throw new NoAppointmentException("you have no appointments for dose2. Book an appointment first");
        //create new dose

        Dose dose=new Dose();
        dose.setDoseId(String.valueOf(UUID.randomUUID()));
        dose.setDoseType(bookDose2RequestDto.getDoseType());
        List<Appointment> lists=person.getAppointments();//change of code
        Appointment appointmentLast=list.get(list.size()-1);//change of code
        appointmentLast.setAppointmentStatus(AppointmentStatus.COMPLETED);//change of code
        dose.setDoseDate(appointmentLast.getAppointmentDate());//change of code
        dose.setPerson(person);

        person.getDoseTaken().add(dose);
        person.setDose2Taken(true);

        Person savedPerson=personRepository.save(person);
        Dose savedDose=savedPerson.getDoseTaken().get(savedPerson.getDoseTaken().size()-1);
        try{
            String text="Congrats!!! "+savedPerson.getName()+". You have taken Dose2. Please make sure you download the certificate." +
                    "\n Be Healthy.Be Happy.";
            SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
            simpleMailMessage.setFrom("pojectbro@gmail.com");
            simpleMailMessage.setTo(savedPerson.getEmailId());
            simpleMailMessage.setSubject("Dose2");
            simpleMailMessage.setText(text);
            javaMailSender.send(simpleMailMessage);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }



        BookDose2ResponseDto ansResponseDto=new BookDose2ResponseDto();
        ansResponseDto.setDoseId(savedDose.getDoseId());
        ansResponseDto.setDoseType(savedDose.getDoseType());
        ansResponseDto.setDoseDate(savedDose.getDoseDate());
        ansResponseDto.setPersonName(savedPerson.getName());

        return ansResponseDto;

    }
}
