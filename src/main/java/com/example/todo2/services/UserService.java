package com.example.todo2.services;

import com.example.todo2.entities.RoleEntity;
import com.example.todo2.entities.UserEntity;
import com.example.todo2.exceptions.UserNotFoundException;
import com.example.todo2.repositories.RoleRepository;
import com.example.todo2.repositories.UserRepository;
import com.example.todo2.requests.CreateUser;
import com.example.todo2.requests.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserEntity create(CreateUser user) {
        var newUser = new UserEntity(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());

        RoleEntity userRole = this.roleRepository.findByName("USER_BASIC");
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        return userRepository.save(newUser);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserEntity find(long id) throws Exception {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new Exception("User not found"));
    }

    public boolean login(LoginUser user) throws UserNotFoundException {
        var userDb = this.userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found [%s]".formatted(user.getUsername())));

        return Objects.equals(userDb.getPassword(), user.getPassword());
    }
}
