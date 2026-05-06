package com.example.carrepairswithticketmanytechmanyspringboo.dto;

import com.example.carrepairswithticketmanytechmanyspringboo.entities.Customer;


public class ToCustomerResponse {
    private CustomerResponse customerResponse;

    public static CustomerResponse toCustomerResponse(Customer customer){
        return CustomerResponse.builder().
                id(customer.getId()).
                address(customer.getAddress()).
                notes(customer.getNotes()).
                active(customer.isActive()).
                userId(customer.getUser().getId()).
                name(customer.getUser().getName()).
                email(customer.getUser().getEmail()).
                phone(customer.getUser().getPhone()).
                image(customer.getUser().getImage()).
                dateOfBirth(customer.getUser().getDateOfBirth()).
                gender(customer.getUser().getGender()).
                build();
    }
}
