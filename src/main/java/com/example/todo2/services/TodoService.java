package com.example.todo2.services;

import com.example.todo2.entities.TodoEntity;
import com.example.todo2.exceptions.TodoNotFoundException;
import com.example.todo2.repositories.TodoRepository;
import com.example.todo2.repositories.UserRepository;
import com.example.todo2.requests.CreateTodo;
import com.example.todo2.requests.UpdateTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    private final UserRepository userRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

//    private List<TodoEntity> todos = new ArrayList<>(Arrays.asList(
//            new TodoEntity(1, "Walk a dog"),
//            new TodoEntity(2, "Walk a cat"),
//            new TodoEntity(3, "Go home")
//    ));

    public List<TodoEntity> getAll() {
        return this.todoRepository.findAll();
    }

    public TodoEntity create(CreateTodo todo) throws Exception {
        var newUser = this.userRepository.findById(todo.getUserId()).orElse(null);

        if (newUser == null) {
            throw new Exception("fail");
        }

        var newTodo = new TodoEntity(todo.getLabel(), newUser);
        this.todoRepository.save(newTodo);

        return newTodo;
    }

    public TodoEntity get(long id) throws TodoNotFoundException {
        var todo = this.todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found [%s]".formatted(id)));


        return todo;
//        var found = this.todos.stream()
//                .filter(currentTodo -> currentTodo.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new TodoNotFoundException("Todo not found [%s]".formatted(id)));
//
//        return found;
    }

    public TodoEntity delete(long id) throws TodoNotFoundException {
        var toDelete = this.get(id);

        this.todoRepository.delete(toDelete);

//        var toDelete = this.todos.stream()
//                .filter(currentTodo -> currentTodo.getId() == id)
//                .findFirst()
//                .orElseThrow(() -> new TodoNotFoundException("Todo not found [%s]".formatted(id)));
//
//        this.todos.remove(toDelete);
//
        return toDelete;
    }

    public TodoEntity update(UpdateTodo todo) throws TodoNotFoundException {
        var toUpdate = this.get(todo.getId());
        toUpdate.setLabel(todo.getLabel());

        this.todoRepository.save(toUpdate);

        return toUpdate;
    }
}
