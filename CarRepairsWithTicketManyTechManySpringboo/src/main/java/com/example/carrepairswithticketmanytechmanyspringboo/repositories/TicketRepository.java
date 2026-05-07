package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Ticket deleteTicketById(UUID id);
    Ticket findTicketById(UUID id);
    List<Ticket> findTicketsByCustomer(Customer customer);
    List<Ticket> findTicketsByCustomerId(UUID customerId);

}
