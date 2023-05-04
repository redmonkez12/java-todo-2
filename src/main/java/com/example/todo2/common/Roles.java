package com.example.todo2.common;

public enum Roles {
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN"),
    BASIC("BASIC");

    private final String label;

    private Roles(String label) {
        this.label = label;
    }
}

