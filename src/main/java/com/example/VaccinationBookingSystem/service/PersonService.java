package com.example.VaccinationBookingSystem.service;

import com.example.VaccinationBookingSystem.DTO.RequestDto.AddPersonRequestDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.AddPersonResponseDto;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GetAllMalesWithAgeGreaterThanResponse;
import com.example.VaccinationBookingSystem.DTO.ResponseDto.GetFemalesWithDose1ResponseDto;
import com.example.VaccinationBookingSystem.ENUM.Gender;
import com.example.VaccinationBookingSystem.customException.PersonNotFoundException;
import com.example.VaccinationBookingSystem.model.Dose;
import com.example.VaccinationBookingSystem.model.Person;
import com.example.VaccinationBookingSystem.repository.DoseRepository;
import com.example.VaccinationBookingSystem.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;
    @Autowired
    DoseRepository doseRepository;

//    public Person addPerson(Person person) {
////           person.setDose2Taken(false);
////           person.setDose1Taken(false);
//           person.setCertificate(null);
//            Person savedPerson= personRepository.save(person);
//            return savedPerson;
//    }
public AddPersonResponseDto addPerson(AddPersonRequestDto addPersonRequestDto) {
//here we cant directly add addpersondto since it is not an entity.so first we have to make a person entity
    //step 1 convert requestDto --->personClass
    Person personObj=new Person();
    personObj.setName(addPersonRequestDto.getName());
    personObj.setAge(addPersonRequestDto.getAge());
    personObj.setEmailId(addPersonRequestDto.getEmailId());
    personObj.setGender(addPersonRequestDto.getGender());
    personObj.setDose1Taken(false);
    personObj.setDose2Taken(false);
    personObj.setCertificate(null);

    Person savedPerson= personRepository.save(personObj);

    //while returning convert entity to addPerson response dto class
    AddPersonResponseDto addPersonResponseDto=new AddPersonResponseDto();
    addPersonResponseDto.setName(savedPerson.getName());
    addPersonResponseDto.setMessage("Congrats! You have been registered");
    return addPersonResponseDto;
}

    public String updateEmail(String oldMail, String newMail) {
    Person person=personRepository.findByEmailId(oldMail);
    if(person==null) throw new PersonNotFoundException("Sorry!!! the email doesn't exist");
    person.setEmailId(newMail);
    personRepository.save(person);
    return "Congrats !!! Your email has been updated successfully.";
    }

    public List<GetAllMalesWithAgeGreaterThanResponse> getAllMalesWithAgeGreaterThan(int age) {
        List<Person> personList=personRepository.findAll();
        List<GetAllMalesWithAgeGreaterThanResponse> responseList=new ArrayList<>();
        for(Person ele:personList){
            if(ele.getAge()>age && ele.getGender().equals(Gender.MALE)){
                GetAllMalesWithAgeGreaterThanResponse pair=new GetAllMalesWithAgeGreaterThanResponse();
                pair.setPersonName(ele.getName());
                pair.setPersonAge(ele.getAge());
                responseList.add(pair);
            }
        }
        return responseList;
    }

    public List<GetFemalesWithDose1ResponseDto> getFemalesWithDose1Only() {
      List<Person>list=personRepository.findAll();
      List<GetFemalesWithDose1ResponseDto> ansList=new ArrayList<>();
      for(Person ele:list){
          if(ele.isDose1Taken()==true && ele.isDose2Taken()==false && ele.getGender().equals(Gender.FEMALE)){
              GetFemalesWithDose1ResponseDto pair=new GetFemalesWithDose1ResponseDto();
              pair.setPersonName(ele.getName());
              pair.setDoseType(ele.getDoseTaken().get(0).getDoseType());
              ansList.add(pair);
          }
      }
      return ansList;
    }

    public List<GetFemalesWithDose1ResponseDto> getAllVaccinated() {
        List<Person>list=personRepository.findAll();
        List<GetFemalesWithDose1ResponseDto> ansList=new ArrayList<>();
        for(Person ele:list){
            if(ele.isDose1Taken() && ele.isDose2Taken()){
                GetFemalesWithDose1ResponseDto pair=new GetFemalesWithDose1ResponseDto();
                pair.setPersonName(ele.getName());
                pair.setDoseType(ele.getDoseTaken().get(0).getDoseType());
                ansList.add(pair);
            }
        }
        return ansList;
    }

    public List<String> noDoseTakenAtAll() {
        List<Person>list=personRepository.findAll();
        List<String> ansList=new ArrayList<>();
        for(Person ele:list){
            if(!ele.isDose1Taken() && !ele.isDose2Taken()){
                ansList.add(ele.getName());
            }
        }
        return ansList;
    }

    public List<GetFemalesWithDose1ResponseDto> getFemaleAgeGreaterThanAndDingleDoseTaken(int age) {
        List<Person>list=personRepository.findAll();
        List<GetFemalesWithDose1ResponseDto> ansList=new ArrayList<>();
        for(Person ele:list){
            if(ele.isDose1Taken() && !ele.isDose2Taken() && ele.getAge()>age){
                GetFemalesWithDose1ResponseDto pair=new GetFemalesWithDose1ResponseDto();
                pair.setPersonName(ele.getName());
                pair.setDoseType(ele.getDoseTaken().get(0).getDoseType());
                ansList.add(pair);
            }
        }
        return  ansList;

    }
}
