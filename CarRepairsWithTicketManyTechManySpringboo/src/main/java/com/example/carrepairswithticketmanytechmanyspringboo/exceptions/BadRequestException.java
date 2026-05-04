package com.example.carrepairswithticketmanytechmanyspringboo.exceptions;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
