package com.example.carrepairswithticketmanytechmanyspringboo.services;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.*;
import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;
import com.example.carrepairswithticketmanytechmanyspringboo.exceptions.NotFoundException;
import com.example.carrepairswithticketmanytechmanyspringboo.mappers.CustomerMapper;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.CustomerRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.repositories.UserRepository;
import com.example.carrepairswithticketmanytechmanyspringboo.utils.ResponseMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public CustomerResponse changeCustomerStatus(UUID id) {
        //----> Get the customer by id.
        var customer = this.getOneCustomerById(id);

        //----> Change the status of the customer and store it in db.
        customer.setActive(!customer.isActive());
        var updatedCustomer = customerRepository.save(customer);

        //----> Return the updated customer.
        return ToCustomerResponse.toCustomerResponse(updatedCustomer);
    }

    @Override
    public CustomerResponse createCustomer(CustomerCreate request) {
        //----> Get the user by id.
        var user = userRepository.getUsersById(request.getUserId());

        //----> Create the customer.
        var customer = customerMapper.toEntity(request);
        customer.setUser(user);
        var savedCustomer = customerRepository.save(customer);

        //----> Return the customer.
        return ToCustomerResponse.toCustomerResponse(savedCustomer);

    }

    @Transactional
    @Override
    public ResponseMessage deleteCustomerById(UUID id) {
        //----> Check for null customer.
        var customer = this.getOneCustomerById(id);

        //----> Delete the customer with the giving id.
        userRepository.deleteUserById(customer.getUser().getId());
        //var deletedCustomer = customerRepository.deleteCustomerById(id);

        //----> Send back response.
        return ResponseMessage.builder().message("Customer deleted successfully.").status("success").statusCode(HttpStatus.OK).build();
    }

    @Override
    public CustomerResponse editCustomerById(UUID id, CustomerEdit request) {
        //----> Get the user by id.
        var user = userRepository.getUsersById(request.getUserId());

        //----> Check for null customer.
        this.getOneCustomerById(id);

        //----> Edit the customer with the giving id.
        var editedCustomer = customerMapper.toEntity(request);
        editedCustomer.setId(id);
        editedCustomer.setUser(user);
        var updatedCustomer = customerRepository.save(editedCustomer);

        //----> Return the updated customer.
        return ToCustomerResponse.toCustomerResponse(updatedCustomer);
    }

    @Override
    public CustomerResponse getCustomerById(UUID id) {
        //----> Get the customer by id.
        var customer = this.getOneCustomerById(id);

        System.out.println("In get-customer-by-id, customer : " + customer);

        //----> Return the customer.
        return ToCustomerResponse.toCustomerResponse(customer);
    }

    @Override
    public CustomerResponse getCustomerByUserId(UUID userId) {
        //----> Fetch customer by user id.
        var customer = customerRepository.getCustomersByUserId(userId);

        //----> Check for null customer.
        if(customer == null) throw new NotFoundException("Customer not found!");

        //----> Return the customer.
        return ToCustomerResponse.toCustomerResponse(customer);
    }

    @Override
    public List<CustomerResponse> getActiveCustomers() {
        //----> Fetch active customers.
        var customers = customerRepository.findActiveCustomers();

        //----> Return the customers.
        return customers.stream().map(ToCustomerResponse::toCustomerResponse).toList();

    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        //----> Fetch all customers.
        var customers = customerRepository.findAll();

        System.out.println("In get-all-customers, customers : " + customers);

        //----> Return the customers.
        return customers.stream().map(ToCustomerResponse::toCustomerResponse).toList();
    }

    @Override
    public List<CustomerResponse> getInactiveCustomers() {
        //----> Fetch active customers.
        var customers = customerRepository.findInactiveCustomers();

        //----> Return the customers.
        return customers.stream().map(ToCustomerResponse::toCustomerResponse).toList();
    }

    private Customer getOneCustomerById(UUID id){
        return customerRepository.findById(id).orElseThrow(()-> new NotFoundException("Customer not found!"));
    }
}
