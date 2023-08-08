package com.example.VaccinationBookingSystem.controller;

import com.example.VaccinationBookingSystem.DTO.RequestDto.AddPersonRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GetAllMalesWithAgeGreaterThanResponse;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GetFemalesWithDose1ResponseDto;
import com.example.VaccinationBookingSystem.model.Person;
import com.example.VaccinationBookingSystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonService personService;
    @PostMapping("/add")
    public ResponseEntity addPerson(@RequestBody AddPersonRequestDto addPersonRequestDto){
        //this might fail in case of duplicate emailId coz we have made the emailId as Unique.
        //so write it in try catch block
        try{
            AddPersonResponseDto addPersonResponseDto=personService.addPerson(addPersonRequestDto);
            return new ResponseEntity(addPersonResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity("email already exists",HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("/update_email")
    public ResponseEntity updateEmail(@RequestParam("old") String oldMail,@RequestParam("new") String newMail){
        try{
           String response=personService.updateEmail(oldMail, newMail);
           return new ResponseEntity(response,HttpStatus.ACCEPTED);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);

        }

    }
    //get all males of age greater than a certain age
    @GetMapping("/get_male_age_greater_than")
    public ResponseEntity getAllMalesWithAgeGreaterThan(@RequestParam int age){
       List<GetAllMalesWithAgeGreaterThanResponse> list= personService.getAllMalesWithAgeGreaterThan(age);
       if(list.size()==0)
           return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
       else
       return new ResponseEntity<>(list,HttpStatus.FOUND);

    }

    //get all female who have taken only dose 1 not dose 2
    @GetMapping("/get_females_onlyWith_dose1")
    public ResponseEntity getFemalesWithDose1Only(){
      List<GetFemalesWithDose1ResponseDto> list=personService.getFemalesWithDose1Only();
      if(list.size()==0)return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
      else
          return new ResponseEntity<>(list,HttpStatus.FOUND);

    }

    //get all the people who are fully vaccinated
    @GetMapping("/get_all_fullyVaccinated")
    public ResponseEntity getAllVaccinated(){
        List<GetFemalesWithDose1ResponseDto> list=personService.getAllVaccinated();
        if(list.size()==0)return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
    //get all the people who have not taken even a single dose
    @GetMapping("/no_dose_taken_at_all")
    public ResponseEntity noDoseTakenAtAll(){
        List<String>list=personService.noDoseTakenAtAll();
        if(list.size()==0)return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);

    }
    //get all females whose age is greater than a particular age and who have taken only dose 1
    @GetMapping("/getFemaleAgeGreaterThan_singleDose")
    public ResponseEntity getFemaleAgeGreaterThanAndDingleDoseTaken(@RequestParam int age){
        List<GetFemalesWithDose1ResponseDto> list=personService.getFemaleAgeGreaterThanAndDingleDoseTaken(age);
        if(list.size()==0)return new ResponseEntity<>("no person satisfies the criteria",HttpStatus.FOUND);
        else
            return new ResponseEntity<>(list,HttpStatus.FOUND);
    }
}
