package com.example.todo2.services;

import com.example.todo2.entities.TodoEntity;
import com.example.todo2.entities.UserEntity;
import com.example.todo2.enums.SortEnum;
import com.example.todo2.exceptions.TodoNotFoundException;
import com.example.todo2.repositories.TodoRepository;
import com.example.todo2.repositories.UserRepository;
import com.example.todo2.requests.CreateTodo;
import com.example.todo2.requests.UpdateTodo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
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

    public List<TodoEntity> getAll(
            Integer page,
            Integer size,
            SortEnum sort,
            String search
    ) {
        var currentSort = sort.name.toUpperCase().equals(SortEnum.ASC.name) ? Sort.by("label").ascending() : Sort.by("label").descending();
        var pagination = PageRequest.of(page, size, currentSort);

        if (search.isEmpty()) {
            return todoRepository.findAll(pagination).stream().toList();
        }


        return todoRepository.findByLabelContainingIgnoreCase(search, pagination);
    }

    public TodoEntity create(CreateTodo todo, long userId) throws Exception {
        var newUser = this.userRepository.findById(userId).orElse(null);

        if (newUser == null) {
            throw new Exception("fail");
        }

        var newTodo = new TodoEntity(todo.getLabel(), newUser);
        this.todoRepository.save(newTodo);

        return newTodo;
    }

    public TodoEntity get(long id) throws TodoNotFoundException {
        return this.todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException("Todo not found [%s]".formatted(id)));
    }

    public TodoEntity getAll(User user) throws TodoNotFoundException {
        var userEntity = this.userRepository.findByEmail(user.getUsername());
        return this.todoRepository.findByUser(userEntity);
    }

    public TodoEntity delete(long id) throws TodoNotFoundException {
        var toDelete = this.get(id);

        this.todoRepository.delete(toDelete);

        return toDelete;
    }

    public TodoEntity update(UpdateTodo todo) throws TodoNotFoundException {
        var toUpdate = this.get(todo.getId());
        toUpdate.setLabel(todo.getLabel());

        this.todoRepository.save(toUpdate);

        return toUpdate;
    }
}
