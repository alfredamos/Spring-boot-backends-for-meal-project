package com.example.carrepairshopspringbackend.repositories;

import com.example.carrepairshopspringbackend.entities.Customer;
import com.example.carrepairshopspringbackend.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findTicketsByTech(String tech);
    List<Ticket> findTicketsByCustomer(Customer customer);
    List<Ticket> findTicketsByCustomerId(UUID customerId);

    @Query("SELECT t FROM Ticket t WHERE  t.completed = true")
    List<Ticket> getCompletedTickets();

    @Query("SELECT t FROM Ticket t WHERE  t.completed = false")
    List<Ticket> getIncompleteTickets();

}
