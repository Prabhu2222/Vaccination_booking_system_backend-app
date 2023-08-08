package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.RequestDto.CenterRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.CenterResponseDto;
import com.example.VaccinationBookingSystem.ENUM.CenterType;
import com.example.VaccinationBookingSystem.model.VaccinationCenter;
import com.example.VaccinationBookingSystem.service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/center")
public class VaccinationCenterController {
    @Autowired
    VaccinationCenterService vaccinationCenterService;
     @PostMapping("/add")
    public CenterResponseDto addCenter(@RequestBody CenterRequestDto centerRequestDto){

        CenterResponseDto centerResponseDto=vaccinationCenterService.addCenter(centerRequestDto);
        return centerResponseDto;

    }
    //get all the doctors at centers of a particular center type
    @GetMapping("/get_doctors_at_centerType")
    public ResponseEntity getDoctorsAtCenterType(@RequestParam CenterType centerType){
        List<String>doctorNameList=vaccinationCenterService.getDoctorsAtCenterType(centerType);
            if(doctorNameList.size()==0)return new ResponseEntity<>("no doctors at this center type", HttpStatus.FOUND);
            else
                return new ResponseEntity<>(doctorNameList,HttpStatus.FOUND);
    }
    //get the center with highest number of doctors
    @GetMapping("/get_centers_high_doc_count")
    public ResponseEntity getCentersWithHighDocCount(){
        List<String> list= vaccinationCenterService.getCentersWithHighDocCount();
        if(list.size()==0)return new ResponseEntity<>("no centers satisfies this criteria", HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
    //get the center with highest number of doctors among a particular centerType
    @GetMapping("/get_high_count_centerType")
    public ResponseEntity getCenterTypeWithHighDocCount(@RequestParam CenterType centerType){
        List<String> list= vaccinationCenterService.getCenterTypeWithHighDocCount(centerType);
        if(list.size()==0)return new ResponseEntity<>("no centers satisfies this criteria", HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
}
