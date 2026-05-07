package com.example.carrepairswithticketmanytechmanyspringboo.dto;

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
public class TicketResponse {
    private UUID id;
    private String title;
    private String description;
    private UUID customerId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String customerImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
