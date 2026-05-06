package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class TechnicianCreate {
    @NotBlank(message = "Specialty must be valid.")
    private String specialty;

    private UUID userId;
}
