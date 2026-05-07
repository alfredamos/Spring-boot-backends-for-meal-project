package com.example.carrepairswithticketmanytechmanyspringboo.dto;

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
public class TicketEdit {
    private UUID id;

    @NotBlank(message = "Name is required!")
    private String title;

    @NotBlank(message = "Notes is required!")
    private String description;

    private UUID customerId;
}
