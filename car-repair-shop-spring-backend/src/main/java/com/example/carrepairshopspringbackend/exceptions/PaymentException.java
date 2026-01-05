package com.example.carrepairshopspringbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
public class PaymentException extends RuntimeException{
    public PaymentException(String message) {
        super(message);
    }
}
