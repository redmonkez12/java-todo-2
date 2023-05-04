package com.example.todo2.controllers;

import com.example.todo2.common.ErrorCodes;
import com.example.todo2.entities.Todo;
import com.example.todo2.requests.CreateTodo;
import com.example.todo2.responses.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path ="api/v1")
public class TodoController {

    private List<Todo> todos = new ArrayList<>(Arrays.asList(
        new Todo(1, "Walk a dog"),
        new Todo(2, "Walk a cat"),
        new Todo(3, "Go home")
    ));

    @PostMapping("/todos")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateTodo todo) {
        try {
            Todo newTodo = new Todo(this.todos.size() + 1, todo.getLabel());
            this.todos.add(newTodo);
            URI location = new URI("/api/v1/todos/" + newTodo.Id());
            return ResponseEntity.created(location).body(newTodo);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(new ErrorResponse(
                            e.getMessage(),
                            ErrorCodes.INTERNAL_SERVER_ERROR,
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ));
        }
    }

    @GetMapping("/todos")
    public ResponseEntity<Object> todos() {
        try {
            return ResponseEntity.ok().body(this.todos);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            e.getMessage(),
                            ErrorCodes.INTERNAL_SERVER_ERROR,
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getBindingResult().getFieldErrors();

        // Process and build the error response based on the field errors
        var errorResponse = new ErrorResponse(
                "Validation error",
                ErrorCodes.BAD_CREDENTIALS,
                HttpStatus.BAD_REQUEST.value()
        );
        List<com.example.todo2.responses.FieldError> errors = new ArrayList<>();

        for (var fieldError : fieldErrors) {
            errors.add(new com.example.todo2.responses.FieldError(fieldError.getField(), fieldError.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
