package com.lex.Car.DTO;

public record AuthResponse(String token, String username, Long exp) {
}