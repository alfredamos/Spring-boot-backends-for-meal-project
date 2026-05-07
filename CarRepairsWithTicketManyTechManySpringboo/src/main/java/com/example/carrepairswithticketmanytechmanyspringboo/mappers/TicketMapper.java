package com.example.carrepairswithticketmanytechmanyspringboo.mappers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.TicketEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toEntity(TicketDto ticketDto);
    Ticket toEntity(TicketCreate request);
    Ticket toEntity(TicketEdit request);

    @Mapping(source = "customer.id", target = "customerId")
    TicketDto toDTO(Ticket ticket);

    List<TicketDto> toDTOList(List<Ticket> tickets);
}
