package com.example.todo2.responses;

public record JwtResponse(String jwtToken, String refreshToken, UserResponse user) {
}

