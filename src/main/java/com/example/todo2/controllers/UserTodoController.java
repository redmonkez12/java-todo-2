package com.example.todo2.controllers;

import com.example.todo2.services.UserTodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserTodoController {

    public final UserTodoService userTodoService;

    @Autowired
    public UserTodoController(UserTodoService userTodoService) {
        this.userTodoService = userTodoService;
    }

    @GetMapping("/{id}/todos")
    public ResponseEntity<Object> all(@PathVariable(name = "id") long id) throws Exception {
        var todos = this.userTodoService.getAll(id);

        return ResponseEntity.ok(todos);
    }
}
