package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerResponse;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;

import java.util.List;
import java.util.UUID;

public interface ICustomerService {
    CustomerResponse changeCustomerStatus(UUID id);
    CustomerResponse createCustomer(CustomerCreate request);
    ResponseMessage deleteCustomerById(UUID id);
    CustomerResponse editCustomerById(UUID id, CustomerEdit request);
    CustomerResponse getCustomerById(UUID id);
    List<CustomerResponse> getActiveCustomers();
    List<CustomerResponse> getAllCustomers();
    List<CustomerResponse> getInactiveCustomers();
}
