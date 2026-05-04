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
public class AssignedTicketCreate {
    public UUID techId;
    public UUID ticketId;
    public Boolean completed;
    public Status status;
    public String assignBy;
    public LocalDateTime assignAt;
}
