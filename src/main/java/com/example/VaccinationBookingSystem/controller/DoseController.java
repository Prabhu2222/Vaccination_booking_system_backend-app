package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.RequestDto.BookDose1RequestDto;
import com.example.VaccinationBookingSystem.DTO.RequestDto.BookDose2RequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookDose1ResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.BookDose2ResponseDto;
import com.example.VaccinationBookingSystem.ENUM.DoseType;
import com.example.VaccinationBookingSystem.customException.DoseAlreadyTakenException;
import com.example.VaccinationBookingSystem.customException.PersonNotFoundException;
import com.example.VaccinationBookingSystem.model.Dose;
import com.example.VaccinationBookingSystem.service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    DoseService doseService;

//    @PostMapping("/get_dose_1")
//    public ResponseEntity getDose1(@RequestParam("id") int personId, @RequestParam("doseType")DoseType doseType){
//        try {
//            Dose doseObj = doseService.getDose1(personId, doseType);
//            return new ResponseEntity<>(doseObj, HttpStatus.CREATED);
////        }
//        }catch (DoseAlreadyTakenException e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }catch (PersonNotFoundException e){
//            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
//        }
////        catch (Exception e){
////            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
////        }
//
//
//    }
@PostMapping("/get_dose_1")
public ResponseEntity getDose1(@RequestBody BookDose1RequestDto bookDose1RequestDto){
    try {
        BookDose1ResponseDto response = doseService.getDose1(bookDose1RequestDto);
        return new ResponseEntity<>(response.toString(), HttpStatus.CREATED);
        }
    catch (DoseAlreadyTakenException e){
        return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
    }catch (PersonNotFoundException e){
        return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
    }catch (Exception e){
        return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
    //api to take dose2
    @PostMapping("/get_dose_2")
    public ResponseEntity getDose2(@RequestBody BookDose2RequestDto bookDose2RequestDto){
          try{
              BookDose2ResponseDto response=doseService.getDose2(bookDose2RequestDto);
              return new ResponseEntity<>(response.toString(),HttpStatus.CREATED);
          }catch(Exception e){
              return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
          }
    }

}
