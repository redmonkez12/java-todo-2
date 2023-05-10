package com.example.todo2.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CreateUser {
    @NotEmpty(message = "First name is mandatory")
    @Size(min=2, max=200, message = "First name must be between 2 and 200 characters long")
    private String firstName;

    @NotEmpty(message = "Last name is mandatory")
    @Size(min=2, max=200, message = "Last name must be between 2 and 200 characters long")
    private String lastName;

    @NotEmpty(message = "Username is mandatory")
    @Size(min=5, max=200, message = "Username must be between 5 and 200 characters long")
    private String username;

    @NotEmpty(message = "Password is mandatory")
    @Size(min=8, max=200, message = "Label must be between 8 and 200 characters long")
    private String password;
}
