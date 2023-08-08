package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.RequestDto.DoctorRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.DoctorResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GeneralDoctorResponseDto;
import com.example.VaccinationBookingSystem.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;
    @PostMapping("/add")
    public ResponseEntity addDoctor(@RequestBody DoctorRequestDto doctorRequestDto){
        try{
            DoctorResponseDto responseDto=doctorService.addDoctor(doctorRequestDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/get_by_age_greater_than")
    public List<String> getByAgeGreaterThan(@RequestParam int age){
        List<String> doctorNameList=doctorService.getByAgeGreaterThan(age);
        return doctorNameList;
    }

//get the doctor with highest number of appointment
    @GetMapping("/get_doctors_highestAppointment")
    public ResponseEntity getDoctorsWithHighestAppointment(){
        List<GeneralDoctorResponseDto> list=doctorService.getDoctorsWithHighestAppointment();
        if(list.size()==0)return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);


    }
    //get list of doctors who belong to a particular center
    @GetMapping("/get_doctors_at_center")
    public ResponseEntity getDoctorsAtCenter(@RequestParam("centerId") int id ){
       List<GeneralDoctorResponseDto> responseList=doctorService.getDoctorsAtCenter(id);
        if(responseList.size()==0)return new ResponseEntity<>("no doctors at this center",HttpStatus.FOUND);
        else
            return new ResponseEntity<>(responseList,HttpStatus.FOUND);

    }

    //api to update email and/or age of a doctor
          //->if u r getting both mail and age both should be updated,if u get either of them
            //then they should be updated

}
