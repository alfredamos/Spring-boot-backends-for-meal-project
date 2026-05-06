package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerEdit;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    CustomerDto createCustomer(CustomerCreate request);
    CustomerDto deleteCustomerById(UUID id);
    CustomerDto editCustomerById(UUID id, CustomerEdit request);
    CustomerDto getCustomerById(UUID id);
    List<CustomerDto> getActiveCustomers();
    List<CustomerDto> getAllCustomers();
    List<CustomerDto> getInactiveCustomers();
}
