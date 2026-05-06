package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Gender;
import com.example.carrepairswithticketmanytechmanyspringboo.validations.ValueOfEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDto {
    private UUID id;

    @NotBlank(message = "Address is required!")
    private String address;

    @NotBlank(message = "Notes is required!")
    private String notes;

    private Boolean active = true;

    private UUID userId;

}
