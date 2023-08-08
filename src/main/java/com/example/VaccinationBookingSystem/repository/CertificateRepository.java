package com.example.VaccinationBookingSystem.repository;

import com.example.VaccinationBookingSystem.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate,Integer> {
}
