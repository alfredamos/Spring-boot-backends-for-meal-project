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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper CustomerMapper;
    private final SameUserAndAdmin sameUserAndAdmin;
    private final CustomerMapper customerMapper;

    public CustomerDto changeCustomerStatus(UUID id){
        //----> Retrieve customer by id.
        var customer = getOneCustomer(id);

        //----> Change customer status.
        customer.setActive(!customer.getActive());
        customer.setId(id);

        //----> Update customer in db.
        var updatedCustomer = customerRepository.save(customer);

        //----> Send back response.
        return customerMapper.toDTO(updatedCustomer);
    }

    public CustomerDto createCustomer(CustomerCreate customer){
        //----> Map customer dto to customer entity.
        var customerToCreate = CustomerMapper.toEntity(customer);

        //----> Include the creator id in the customer object.
        var user = sameUserAndAdmin.getUserFromContext();
        customerToCreate.setUser(user);
        customerToCreate.setActive(true);
        customerToCreate.setCreatedAt(LocalDateTime.now());
        customerToCreate.setUpdatedAt(LocalDateTime.now());

        //----> Save the new customer in the database
        return customerMapper.toDTO(customerRepository.save(customerToCreate));
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
        customerToEdit.setUpdatedAt(LocalDateTime.now());

        //----> Retrieve customer by id.
        getOneCustomer(id);

        //----> Update customer in db.
        customerToEdit.setId(id);
        customerRepository.save(customerToEdit);

        //----> Send back response.
        return new ResponseMessage("Customer has been updated successfully!", "success", HttpStatus.OK);
    }

    public CustomerDto getCustomerById(UUID id){
        //----> Retrieve customer by id.
        return customerMapper.toDTO(getOneCustomer(id));
    }

    public List<CustomerDto> getAllCustomers(){
        //----> Retrieve all customers.
        return customerMapper.toDTOList(customerRepository.findAll());
    }

    public List<CustomerDto> getActiveCustomers(){
        //----> Retrieve active customers.
        return customerMapper.toDTOList(customerRepository.findActiveCustomers());
    }

    public List<CustomerDto> getInactiveCustomers(){
        //----> Retrieve inactive customers.
        return customerMapper.toDTOList(customerRepository.findInactiveCustomers());
    }

    private Customer getOneCustomer(UUID id){
        //----> Retrieve customer by id.
        return customerRepository.findById(id).orElseThrow(() -> new NotFoundException("Customer not found!"));

    }
}
