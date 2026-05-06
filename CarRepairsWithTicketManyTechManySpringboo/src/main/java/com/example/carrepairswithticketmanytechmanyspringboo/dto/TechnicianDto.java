package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicianDto {
    private UUID id;

    @NotBlank(message = "Specialty is required!")
    private String specialty;

    private UUID userId;
}
