package com.example.todo2.requests;

import jakarta.validation.constraints.NotEmpty;

public record JwtRequest(
        @NotEmpty(message = "Email is required") String email,
        @NotEmpty(message = "Password is required") String password) {
}

