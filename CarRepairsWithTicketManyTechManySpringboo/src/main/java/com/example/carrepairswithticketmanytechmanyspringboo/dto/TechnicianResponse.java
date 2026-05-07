package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicianResponse {
    private UUID id;
    private String specialty;
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private String image;
    private LocalDateTime dateOfBirth;
    private Gender gender;
}
