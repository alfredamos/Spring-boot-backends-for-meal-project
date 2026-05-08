package com.example.carrepairswithticketmanytechmanyspringboo.controllers;

import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerCreate;
import com.example.carrepairswithticketmanytechmanyspringboo.dto.CustomerEdit;
import com.example.carrepairswithticketmanytechmanyspringboo.services.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/customers")
@RequiredArgsConstructor
@RestController
public class CustomerController {
    private final ICustomerService customerService;

    @PostMapping
    public ResponseEntity<?> createCustomer(@Valid @RequestBody  CustomerCreate customerCreate){
        var response = customerService.createCustomer(customerCreate);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PatchMapping("/change-status/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> changeCustomerStatusById(@PathVariable UUID id){
        var response = customerService.changeCustomerStatus(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> deleteCustomerById(@PathVariable UUID id){
        var response = customerService.deleteCustomerById(id);

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByUserId(#id)")
    public ResponseEntity<?> editCustomerById(@PathVariable UUID id, @RequestBody @Valid CustomerEdit customerEdit){
        var response = customerService.editCustomerById(id, customerEdit);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByUserId(#id)")
    public ResponseEntity<?> getCustomerById(@PathVariable UUID id){
        var response = customerService.getCustomerById(id);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/by-user-id/{userId}")
    @PreAuthorize("@sameUserAndAdmin.checkForOwnerOrAdminByUserId(#userId)")
    public ResponseEntity<?> getCustomerByUserId(@PathVariable UUID userId){
        var response = customerService.getCustomerByUserId(userId);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getAllCustomers(){
        var response = customerService.getAllCustomers();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/all/active")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getActiveCustomers(){
        var response = customerService.getActiveCustomers();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/all/inactive")
    @PreAuthorize("@sameUserAndAdmin.checkForAdmin()")
    public ResponseEntity<?> getInactiveCustomers(){
        var response = customerService.getInactiveCustomers();

        return ResponseEntity.ok(response);

    }

}
