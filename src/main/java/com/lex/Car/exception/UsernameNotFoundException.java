package com.lex.Car.exception;

public class UsernameNotFoundException extends RuntimeException{
    UsernameNotFoundException(String message){
        super(message);
    }
}
