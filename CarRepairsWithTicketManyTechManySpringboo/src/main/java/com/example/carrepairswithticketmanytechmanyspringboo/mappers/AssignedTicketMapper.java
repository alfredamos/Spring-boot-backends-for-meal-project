package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.AssignedTicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.AssignedTicket;
import org.mapstruct.Mapping;

import java.util.List;

public interface AssignedTicketMapper {
    AssignedTicket toEntity(AssignedTicketDto assignedTicketDto);
    AssignedTicket toEntity(AssignedTicketCreate request);
    AssignedTicket toEntity(AssignedTicketEdit request);

    @Mapping(source = "tech.id", target = "techId")
    @Mapping(source = "ticket.id", target = "ticketId")
    AssignedTicketDto toDTO(AssignedTicket assignedTicket);

    List<AssignedTicketDto> toDTOList(List<AssignedTicket> assignedTickets);
}
