package com.example.carrepairswithticketmanytechmanyspringboo.repositories;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer getCustomersByUserId(UUID userId);
    Customer getCustomerById(UUID id);
    @Query("SELECT c FROM Customer c WHERE  c.active = false ")
    List<Customer> findInactiveCustomers();

    @Query("SELECT c FROM Customer c WHERE  c.active = true")
    List<Customer> findActiveCustomers();

}
