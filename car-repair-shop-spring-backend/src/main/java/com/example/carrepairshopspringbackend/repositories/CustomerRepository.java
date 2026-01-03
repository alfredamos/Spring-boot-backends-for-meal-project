package com.example.carrepairshopspringbackend.repositories;

import com.example.carrepairshopspringbackend.entities.Customer;
import com.example.carrepairshopspringbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface
CustomerRepository extends JpaRepository<Customer, UUID> {
    Customer findCustomerByEmail(String email);
    void deleteById(UUID id);

    @Query("SELECT c FROM Customer c WHERE  c.active = false ")
    List<Customer> findInactiveCustomers();

    @Query("SELECT c FROM Customer c WHERE  c.active = true")
    List<Customer> findActiveCustomers();
}
