package com.example.carrepairshopspringbackend.services;

import com.example.carrepairshopspringbackend.dtos.CustomerCreate;
import com.example.carrepairshopspringbackend.dtos.CustomerDto;
import com.example.carrepairshopspringbackend.entities.Customer;
import com.example.carrepairshopspringbackend.exceptions.NotFoundException;
import com.example.carrepairshopspringbackend.mapper.CustomerMapper;
import com.example.carrepairshopspringbackend.repositories.CustomerRepository;
import com.example.carrepairshopspringbackend.utils.ResponseMessage;
import com.example.carrepairshopspringbackend.utils.SameUserAndAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper CustomerMapper;
    private final SameUserAndAdmin sameUserAndAdmin;

    public ResponseMessage changeCustomerStatus(UUID id){
        //----> Retrieve customer by id.
        var customer = getOneCustomer(id);

        //----> Change customer status.
        customer.setActive(!customer.getActive());
        customer.setId(id);

        //----> Update customer in db.
        customerRepository.save(customer);

        //----> Send back response.
        return new ResponseMessage("Customer status has been changed successfully!", "success", HttpStatus.OK);
    }

    public Customer createCustomer(CustomerCreate customer){
        //----> Map customer dto to customer entity.
        var customerToCreate = CustomerMapper.toEntity(customer);

        //----> Include the creator id in the customer object.
        var user = sameUserAndAdmin.getUserFromContext();
        customerToCreate.setUser(user);

        //----> Save the new customer in the database
        return customerRepository.save(customerToCreate);
    }

    public ResponseMessage deleteCustomerById(UUID id){
        //----> Retrieve customer by id.
        getOneCustomer(id);

        //----> Delete customer.
        customerRepository.deleteById(id);

        //----> Send back response.
        return new ResponseMessage("Customer has been deleted successfully!", "success", HttpStatus.OK);
    }

    public ResponseMessage editCustomerById(UUID id, CustomerDto customer){
        //----> Map customer dto to customer entity.
        var customerToEdit = CustomerMapper.toEntity(customer);

        //----> Include the updater id in the customer object.
        var user = sameUserAndAdmin.getUserFromContext();
        customerToEdit.setUser(user);

        //----> Retrieve customer by id.
        getOneCustomer(id);

        //----> Update customer in db.
        customerToEdit.setId(id);
        customerRepository.save(customerToEdit);

        //----> Send back response.
        return new ResponseMessage("Customer has been updated successfully!", "success", HttpStatus.OK);
    }

    public Customer getCustomerById(UUID id){
        //----> Retrieve customer by id.
        return getOneCustomer(id);
    }

    public List<Customer> getAllCustomers(){
        //----> Retrieve all customers.
        return customerRepository.findAll();
    }

    public List<Customer> getActiveCustomers(){
        //----> Retrieve active customers.
        return customerRepository.findActiveCustomers();
    }

    public List<Customer> getInactiveCustomers(){
        //----> Retrieve inactive customers.
        return customerRepository.findInactiveCustomers();
    }

    private Customer getOneCustomer(UUID id){
        //----> Retrieve customer by id.
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found!"));

    }
}
