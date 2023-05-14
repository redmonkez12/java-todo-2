package com.example.todo2.services;

import com.example.todo2.entities.RoleEntity;
import com.example.todo2.entities.UserEntity;
import com.example.todo2.exceptions.UserNotFoundException;
import com.example.todo2.repositories.RoleRepository;
import com.example.todo2.repositories.UserRepository;
import com.example.todo2.requests.CreateUser;
import com.example.todo2.requests.LoginUser;
import com.example.todo2.responses.UserResponse;
import jakarta.transaction.Transactional;
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

    public UserResponse getUserResponse(UserEntity userEntity) {
        return new UserResponse(
                userEntity.getEmail(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getId(),
                userEntity.getRoles()
        );
    }

    @Transactional(rollbackOn = Exception.class)
    public UserEntity saveUser(RegisterRequest registerRequest) throws Exception {
        try {
            UserEntity user = new UserEntity();

            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            RoleEntity userRole = roleRepository.findByRole("USER_BASIC");
            user.setRoles(new HashSet<RoleEntity>(Collections.singletonList(userRole)));
            return userRepository.save(user);
        } catch (Exception e) {
            throw new Error(String.format("User was not created %s %s", registerRequest.getEmail(), e.getClass()));
        }
    }

}
