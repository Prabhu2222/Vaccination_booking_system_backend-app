package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.DTO.ResponseDto.GetFemalesWithDose1ResponseDto;
import com.example.VaccinationBookingSystem.model.Dose;
import com.example.VaccinationBookingSystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person,Integer> {

    Person findByEmailId(String email);//since email is unique we will only get personobject
    List<Person> findByNameAndAge(String name, Integer age);


    //first parameter should be name and second should be age
    //or else error will come;
    //here return type is list<Person> since we know that there can be many person with same name or same age
    //or same name and age .This is because name and age are no  primary key(pk) and there may
    // exists duplicates. so carefully decide the return type;
    //the implementation of above will be done by orm

}
