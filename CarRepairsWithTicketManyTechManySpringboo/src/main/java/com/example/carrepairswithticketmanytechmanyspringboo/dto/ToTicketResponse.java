package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;

public class ToTicketResponse {
    static public TicketResponse toTicketResponse(Ticket ticket){
        return TicketResponse.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .customerId(ticket.getCustomer().getId())
                .customerImage(ticket.getCustomer().getUser().getImage())
                .customerName(ticket.getCustomer().getUser().getName())
                .customerPhone(ticket.getCustomer().getUser().getPhone())
                .customerEmail(ticket.getCustomer().getUser().getEmail())
                .createdAt(ticket.getCreatedAt())
                .updatedAt(ticket.getUpdatedAt())
                .build();
    }
}
