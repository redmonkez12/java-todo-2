package com.example.todo2.enums;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER"),
    SUPER_ADMIN("SUPER_ADMIN");

    public final String name;

    private RoleEnum(String name) {
        this.name = name;
    }
}
