package com.lex.Car.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with ID: " + id);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
