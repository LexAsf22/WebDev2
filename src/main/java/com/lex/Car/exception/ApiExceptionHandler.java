package com.lex.Car.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handle(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", java.time.ZonedDateTime.now());
        body.put("status", ex.getStatusCode().value());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,Object>> handleBadCredentials(BadCredentialsException ex) {
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", java.time.ZonedDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", "Invalid username or password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String,Object>> handleAuthenticationException(AuthenticationException ex){
        Map<String,Object> body = new HashMap<>();
        body.put("timestamp", java.time.ZonedDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", "Unauthorized");
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}