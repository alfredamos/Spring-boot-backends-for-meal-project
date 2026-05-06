package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
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
public class CustomerResponse {
    private UUID id;
    private String address;
    private boolean active;
    private String notes;
    private UUID userId;
    private String name;
    private String email;
    private String phone;
    private String image;
    private LocalDateTime dateOfBirth;
    private Gender gender;

}
