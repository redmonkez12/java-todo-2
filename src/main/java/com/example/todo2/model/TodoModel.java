package com.example.todo2.model;

import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoModel {
    public long id;

    public String label;
}
