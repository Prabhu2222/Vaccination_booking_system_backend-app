package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.model.Doctor;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    //using custom queries

    @Query(value="select * from doctor_table where age> :age",nativeQuery = true)//colon age is used to represent age variable
    List<Doctor> getByAgeGreaterThan(int age);
    //    @Query(value="select d from Doctor as d where d.age> :age")//since this is not a native query we don't need to write tha
//    List<Doctor> getByAgeGreaterThan(int age);
    @Query(value="select name from doctor_table where age> :age",nativeQuery = true)
    List<String> getByAgeGreaterThanByColumn(int age);

   @Query(value = "select name from doctor_table where center_id= :ele ",nativeQuery = true)
    List<String> getList(int ele);
}
