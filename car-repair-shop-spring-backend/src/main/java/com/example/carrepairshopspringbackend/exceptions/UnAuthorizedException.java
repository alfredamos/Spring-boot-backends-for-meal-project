package com.example.carrepairshopspringbackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@NoArgsConstructor
//@AllArgsConstructor
public class UnAuthorizedException extends RuntimeException{

    public UnAuthorizedException(String message) {
        super(message);
    }
}
