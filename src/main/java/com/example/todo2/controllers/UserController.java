package com.example.todo2.controllers;

import com.example.todo2.common.ErrorCodes;
import com.example.todo2.requests.CreateUser;
import com.example.todo2.responses.ErrorResponse;
import com.example.todo2.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> create(@RequestBody @Valid CreateUser user) {
        try {
            var newUser = this.userService.create(user);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(
                            "Something went wrong",
                            ErrorCodes.INTERNAL_SERVER_ERROR,
                            HttpStatus.INTERNAL_SERVER_ERROR.value()
                    ));
        }
    }

    @PatchMapping("/auth")
    public ResponseEntity<Object> login() {
        return ResponseEntity.ok(null);
    }
}
