package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findTicketsByCustomer(Customer customer);
    List<Ticket> findTicketsByCustomerId(UUID customerId);

    @Query("SELECT t FROM Ticket t WHERE  t.completed = true")
    List<Ticket> getCompletedTickets();

    @Query("SELECT t FROM Ticket t WHERE  t.completed = false")
    List<Ticket> getIncompleteTickets();

}
