package com.alfredamos.meal_order.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadCredentialException extends RuntimeException{
    public BadCredentialException(String message) {
        super(message);

    }
}
