package com.example.todo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.sql.Timestamp;

@Entity(name="todos")
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties("user")
@EnableJpaAuditing
public class TodoEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="label", unique = true, nullable = false, length = 150)
    private String label;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserEntity user;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Timestamp  updatedAt;

    public TodoEntity(String label) {
        this.label = label;
    }

    public TodoEntity(String label, UserEntity user) {
        this.label = label;
        this.user = user;
    }
}
