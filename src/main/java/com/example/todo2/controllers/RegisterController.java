package com.example.todo2.controllers;

import com.example.todo2.common.ErrorCodes;
import com.example.todo2.responses.ErrorResponse;
import com.example.todo2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path ="api/v1")
public class RegisterController {

    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterRequest registerRequest) throws Exception {
        try {
            userService.saveUser(registerRequest);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(
                    String.format("User [%s] already exists", registerRequest.getEmail()),
                    ErrorCodes.USER_DUPLICATION,
                    HttpStatus.CONFLICT.value()
            ));
        }
    }
}

