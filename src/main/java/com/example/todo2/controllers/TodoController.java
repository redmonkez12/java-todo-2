package com.example.todo2.controllers;

import com.example.todo2.common.ErrorCodes;
import com.example.todo2.entities.UserEntity;
import com.example.todo2.enums.SortEnum;
import com.example.todo2.exceptions.TodoNotFoundException;
import com.example.todo2.requests.CreateTodo;
import com.example.todo2.requests.UpdateTodo;
import com.example.todo2.responses.ErrorResponse;
import com.example.todo2.services.TodoService;
import com.example.todo2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path ="api/v1")
public class TodoController {

    private final TodoService todoService;

    private final UserService userService;

    @Autowired
    public TodoController(TodoService todoService, UserService userService) {
        this.todoService = todoService;
        this.userService = userService;
    }

    @PostMapping("/users/todos")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateTodo todo, Authentication authentication) throws URISyntaxException, Exception {
        var email = authentication.getName();
        var user = this.userService.findUserByEmail(email);

        var newTodo = this.todoService.create(todo, user.getId());
        var location = new URI("/api/v1/users/todos/" + newTodo.getId());

        return ResponseEntity.created(location).body(newTodo);
    }

    @PatchMapping("/todos")
    public ResponseEntity<Object> update(@RequestBody @Valid UpdateTodo todo) throws TodoNotFoundException {
        var updatedTodo = this.todoService.update(todo);

        return ResponseEntity.ok(updatedTodo);
    }

    @GetMapping("/users/todos")
    public ResponseEntity<Object> todo(Authentication authentication) throws TodoNotFoundException
    {
        var user = authentication.getPrincipal();
        var todos = this.todoService.getAll((User)user);

        return ResponseEntity.ok(todos);
    }


    @DeleteMapping("/todos/:id")
    public ResponseEntity<Object> delete(@PathVariable long id) throws TodoNotFoundException
    {
        this.todoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/todos")
    public ResponseEntity<Object> todos(
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("search") Optional<String> search,
            @RequestParam("sort") Optional<SortEnum> sort

    ) {
        var todos = this.todoService.getAll(
                page.orElse(0),
                size.orElse(10),
                sort.orElse(SortEnum.ASC),
                search.orElse("")

        );

        return ResponseEntity.ok().body(todos);
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleValidationException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        "Something went wrong",
                        ErrorCodes.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(TodoNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        ex.getMessage(),
                        ErrorCodes.TODO_NOT_FOUND,
                        HttpStatus.CONFLICT.value()
                ));
    }
}
