package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Status;
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
public class AssignedTicketResponse {
    private UUID techId;
    private UUID ticketId;
    private Status status;
    private boolean completed;
    private String assignBy;
    private LocalDateTime assignAt;
    private String ticketTitle;
    private String ticketNotes;
    private String customerName;
    private String customerEmail;
    private String customerAddress;
    private String customerPhone;
    private String customerImage;
    private String techName;
    private String techEmail;
    private String techPhone;
    private String techSpecialty;
    private String techImage;

}
