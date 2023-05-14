package com.example.todo2.responses;

import com.example.todo2.entities.RoleEntity;

import java.util.Set;
import java.util.UUID;

public record UserResponse(String email, String firstname, String lastName, long id, Set<RoleEntity> roles) {
}

