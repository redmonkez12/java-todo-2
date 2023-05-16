package com.example.todo2.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class RegisterRequest {
    @NotEmpty
    @Size(min=6, max=16, message = "Username must be between 6 and 16 characters long")
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotEmpty
    @Size(min=8, max=100, message = "Password must be between 8 and 100 characters long")
    private String password;

    private String firstName;

    private String lastName;
}

