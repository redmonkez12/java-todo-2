package com.example.todo2.services;

import com.example.todo2.entities.UserEntity;
import com.example.todo2.exceptions.UserNotFoundException;
import com.example.todo2.repositories.UserRepository;
import com.example.todo2.requests.CreateUser;
import com.example.todo2.requests.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(CreateUser user) {
        var newUser = new UserEntity(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());

        this.userRepository.save(newUser);

        return newUser;
    }

    public boolean login(LoginUser user) throws UserNotFoundException {
        var userDb = this.userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found [%s]".formatted(user.getUsername())));

        if (userDb == userDb) {
            return true;
        }

        return false;
    }
}
