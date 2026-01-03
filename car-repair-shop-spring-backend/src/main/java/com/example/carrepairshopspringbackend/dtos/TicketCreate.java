package com.example.carrepairshopspringbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketCreate {
    @NotBlank(message = "Name is required!")
    private String title;

    @NotBlank(message = "Technician is required!")
    private String tech;

    @NotBlank(message = "Notes is required!")
    private String notes;

    private UUID customerId;
}
