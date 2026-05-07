package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface TechnicianRepository extends JpaRepository<Technician, UUID> {
    List<Technician> getTechniciansBySpecialty(String specialty);
    Technician getTechniciansByUserId(UUID userId);
}
