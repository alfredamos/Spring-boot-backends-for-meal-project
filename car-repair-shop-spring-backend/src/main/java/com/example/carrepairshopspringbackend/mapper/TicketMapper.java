package com.example.carrepairshopspringbackend.mapper;

import com.example.carrepairshopspringbackend.dtos.CustomerCreate;
import com.example.carrepairshopspringbackend.dtos.CustomerDto;
import com.example.carrepairshopspringbackend.dtos.TicketCreate;
import com.example.carrepairshopspringbackend.dtos.TicketDto;
import com.example.carrepairshopspringbackend.entities.Customer;
import com.example.carrepairshopspringbackend.entities.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    Ticket toEntity(TicketDto ticketDto);
    Ticket toEntity(TicketCreate request);

    @Mapping(source = "customer.id", target = "customerId")
    TicketDto toDTO(Ticket ticket);

    List<TicketDto> toDTOList(List<Ticket> tickets);
}
