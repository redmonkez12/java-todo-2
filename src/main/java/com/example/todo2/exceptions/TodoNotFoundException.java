package com.example.todo2.exceptions;

public class TodoNotFoundException extends Exception {
    public TodoNotFoundException(String message) {
        super(message);
    }

}
