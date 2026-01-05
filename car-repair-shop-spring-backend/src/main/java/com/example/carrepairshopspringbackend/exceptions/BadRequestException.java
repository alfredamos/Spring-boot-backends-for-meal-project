package com.example.carrepairshopspringbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
