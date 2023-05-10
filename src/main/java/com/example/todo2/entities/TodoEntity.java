package com.example.todo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="todos")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties("user")
public class TodoEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="label", unique = true, nullable = false, length = 150)
    private String label;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserEntity user;

    public TodoEntity(String label) {
        this.label = label;
    }

    public TodoEntity(String label, UserEntity user) {
        this.label = label;
        this.user = user;
    }
}
