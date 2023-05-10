package com.example.todo2.repositories;

import com.example.todo2.entities.TodoEntity;
import com.example.todo2.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>  {
    Optional<UserEntity> findByUsername(String username);
}
