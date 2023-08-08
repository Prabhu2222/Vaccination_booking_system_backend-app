package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.model.Dose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoseRepository extends JpaRepository<Dose,Integer> {
}
