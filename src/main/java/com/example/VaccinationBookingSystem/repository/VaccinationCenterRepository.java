package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.ENUM.CenterType;
import com.example.VaccinationBookingSystem.model.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter,Integer> {

}
