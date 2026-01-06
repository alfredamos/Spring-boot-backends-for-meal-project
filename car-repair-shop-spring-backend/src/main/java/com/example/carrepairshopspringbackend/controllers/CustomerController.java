package com.example.carrepairshopspringbackend.controllers;

import com.example.carrepairshopspringbackend.dtos.CustomerCreate;
import com.example.carrepairshopspringbackend.dtos.CustomerDto;
import com.example.carrepairshopspringbackend.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
   private final CustomerService customerService;

   @PostMapping
   public ResponseEntity<?> createCustomer(@RequestBody CustomerCreate request){
       //----> Save the new customer in db.
       var response = customerService.createCustomer(request);

       //----> Send back response.
       return ResponseEntity.ok(response);
   }

    @PatchMapping("/change-status/{id}")
    public ResponseEntity<?> changeCustomerStatus(@PathVariable UUID id){
        //----> Change the customer status.
        var response = customerService.changeCustomerStatus(id);

        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable UUID id){
        //----> Delete the customer by id.
        var response = customerService.deleteCustomerById(id);

        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editCustomerById(@PathVariable UUID id, @RequestBody CustomerDto request){
        //----> Save the new customer in db.
        var response = customerService.editCustomerById(id, request);

        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID id){
       //----> Get the customer by id.
        var response = customerService.getCustomerById(id);

        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(){
       //----> Get all customers.
        var response = customerService.getAllCustomers();
        System.out.println("In get all customers, response : " + response);
        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-active/get-all-active-customers")
    public ResponseEntity<?> getActiveCustomers(){
        //----> Get all active customers.
        var response = customerService.getActiveCustomers();

        //----> Send back response.
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-inactive/get-all-inactive-customers")
    public ResponseEntity<?> getInactiveCustomers(){
        //----> Get all inactive customers.
        var response = customerService.getInactiveCustomers();

        //----> Send back response.
        return ResponseEntity.ok(response);
    }
}
