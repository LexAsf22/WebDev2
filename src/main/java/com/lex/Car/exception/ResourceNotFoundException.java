package com.lex.Car.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, int id) {
        super(resource + " with ID " + id + " not found.");
    }

    public ResourceNotFoundException(String resource) {
        super(resource + " not found.");
    }
}
