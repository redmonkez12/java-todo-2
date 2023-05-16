package com.example.todo2.controllers;

import com.example.todo2.services.UserTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserTodoController {

    public final UserTodoService userTodoService;

    @Autowired
    public UserTodoController(UserTodoService userTodoService) {
        this.userTodoService = userTodoService;
    }

//    @GetMapping("/todos")
//    public ResponseEntity<Object> all() throws Exception {
//        long id = 1;
//
//        var todos = this.userTodoService.getAll(id);
//
//        return ResponseEntity.ok(todos);
//    }

//    @PostMapping("/todos")
//    public ResponseEntity<?> create() {
//
//    }
}
