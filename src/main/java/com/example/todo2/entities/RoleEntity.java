package com.example.todo2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="roles")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoleEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    public RoleEntity(String name) {
        this.name = name;
    }
}
