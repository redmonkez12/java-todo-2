package com.example.todo2.repositories;

import com.example.todo2.entities.TodoEntity;
import com.example.todo2.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

//    List<TodoEntity> findAll(long id, Pageable pageable, Sort sort);
    List<TodoEntity> findByLabelContainingIgnoreCase(String label, Pageable pageable);

    @Query("SELECT t FROM todos t WHERE t.user = :user")
    List<TodoEntity> findAllByUser(@Param("user") UserEntity user);

    @Query("SELECT t FROM todos t WHERE t.user = :user")
    TodoEntity findByUser(@Param("user") UserEntity user);
}
