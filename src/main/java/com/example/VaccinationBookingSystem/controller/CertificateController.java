package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.ResponseDto.CertificateResponseDto;
import com.example.VaccinationBookingSystem.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/certificate")
public class CertificateController {
    @Autowired
    CertificateService certificateService;
    @GetMapping("/get")
    public ResponseEntity getCertificate(@RequestParam int personId){
        try{
            CertificateResponseDto response=certificateService.getCertificate(personId);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}
