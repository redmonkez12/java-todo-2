package com.example.todo2.enums;

public enum SortEnum {
    ASC("ASC"),
    DESC("DESC"),

    desc("desc"),

    asc("asc");

    public final String name;

    private SortEnum(String name) {
        this.name = name;
    }
}
