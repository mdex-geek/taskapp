package com.todo.backend.reposistory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.backend.entity.UserEntity;

@Repository
public interface UserReposistory extends JpaRepository<UserEntity,String>{

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
