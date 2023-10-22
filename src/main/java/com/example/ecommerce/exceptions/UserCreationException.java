package com.example.ecommerce.exceptions;

public class UserCreationException extends RuntimeException {
    public UserCreationException(String message) {
        super(message);
    }
}