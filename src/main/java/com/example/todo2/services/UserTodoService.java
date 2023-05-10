package com.example.todo2.services;

import com.example.todo2.entities.TodoEntity;
import com.example.todo2.repositories.TodoRepository;
import com.example.todo2.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTodoService {

    private final TodoRepository todoRepository;

    private final UserService userService;

    @Autowired
    public UserTodoService(TodoRepository todoRepository, UserService userService) {
        this.todoRepository = todoRepository;
        this.userService = userService;
    }

    public List<TodoEntity> getAll(long userId) throws Exception {
        var user = this.userService.find(userId);

        return this.todoRepository.findByUser(user);
    }

//    public List<TodoEntity> get(long userId, long  todoId) {
//
//    }
}
