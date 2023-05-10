package com.example.todo2.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class CreateTodo {
    @NotEmpty(message = "Label is mandatory")
    @Size(min=2, max=200, message = "Label must be between 2 and 200 characters long")
    private String label;

    @NotNull(message = "User id is mandatory")
    private long userId;
}
