package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerDto;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.CustomerMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.CustomerRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;

    @Override
    public CustomerDto createCustomer(CustomerCreate request) {
        //----> Get the user by id.
        var user = userRepository.getUsersById(request.getUserId());

        //----> Create the customer.
        var customer = customerMapper.toEntity(request);
        customer.setUser(user);
        var savedCustomer = customerRepository.save(customer);

        //----> Return the customer.
        return customerMapper.toDTO(savedCustomer);

    }

    @Override
    public CustomerDto deleteCustomerById(UUID id) {
        //----> Check for null customer.
        this.getOneCustomerById(id);

        //----> Delete the customer with the giving id.
        var deletedCustomer = customerRepository.deleteCustomerById(id);

        //----> Return the deleted customer.
        return customerMapper.toDTO(deletedCustomer);
    }

    @Override
    public CustomerDto editCustomerById(UUID id, CustomerEdit request) {
        //----> Check for null customer.
        this.getOneCustomerById(id);

        //----> Edit the customer with the giving id.
        var editedCustomer = customerMapper.toEntity(request);
        editedCustomer.setId(id);
        var updatedCustomer = customerRepository.save(editedCustomer);

        //----> Return the updated customer.
        return customerMapper.toDTO(updatedCustomer);
    }

    @Override
    public CustomerDto getCustomerById(UUID id) {
        //----> Get the customer by id.
        var customer = this.getOneCustomerById(id);

        //----> Return the customer.
        return customerMapper.toDTO(customer);
    }

    @Override
    public List<CustomerDto> getActiveCustomers() {
        //----> Fetch active customers.
        var customers = customerRepository.findActiveCustomers();

        //----> Return the customers.
        return customerMapper.toDTOList(customers);

    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        //----> Fetch all customers.
        var customers = customerRepository.findAll();

        //----> Return the customers.
        return customerMapper.toDTOList(customers);
    }

    @Override
    public List<CustomerDto> getInactiveCustomers() {
        //----> Fetch active customers.
        var customers = customerRepository.findInactiveCustomers();

        //----> Return the customers.
        return customerMapper.toDTOList(customers);
    }

    private Customer getOneCustomerById(UUID id){
        return customerRepository.findById(id).orElseThrow(()-> new NotFoundException("Customer not found!"));
    }
}
