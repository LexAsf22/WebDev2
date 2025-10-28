package com.lex.Car.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resource, int id){
        super(resource + " with the id " + id + " does not exist");
    }
}