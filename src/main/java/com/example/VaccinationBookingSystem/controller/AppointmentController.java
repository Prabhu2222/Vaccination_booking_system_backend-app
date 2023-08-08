package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.RequestDto.BookAppointmentRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookAppointmentResponseDto;
import com.example.VaccinationBookingSystem.customException.DoctorNotFoundException;
import com.example.VaccinationBookingSystem.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping("/book")
    public ResponseEntity bookAppointment(@RequestBody BookAppointmentRequestDto bookAppointmentRequestDto){
        try{
            BookAppointmentResponseDto response= appointmentService.bookAppointment(bookAppointmentRequestDto);
            return new ResponseEntity(response, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    //get all the appointment of a particular doctor
    @GetMapping("/get_doctor's")
    public ResponseEntity getDoctorAppointmentById(@RequestParam int doctorId){
        try{
            List<BookAppointmentResponseDto> responseList=appointmentService.getDoctorAppointmentById(doctorId);
            return new ResponseEntity<>(responseList,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity (e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }
    //get all appointment of a particular person
    @GetMapping("/get_person's")
    public ResponseEntity getPersonAppointmentById(@RequestParam int personId){
        try{
            List<BookAppointmentResponseDto> responseList=appointmentService.getPersonAppointmentById(personId);
            return new ResponseEntity<>(responseList,HttpStatus.FOUND);
        }catch (Exception e){
            return new ResponseEntity (e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/cancel")
    public ResponseEntity cancelAppointment(@RequestParam String id){
        try{
            appointmentService.cancelAppointment(id);
            return new ResponseEntity<>("appointment cancelled",HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}
