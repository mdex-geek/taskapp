package com.todo.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.backend.dto.auth.SignUpUserRequestDto;
import com.todo.backend.entity.UserEntity;
import com.todo.backend.exception.EmailAlreadyExistsException;
import com.todo.backend.reposistory.UserReposistory;

@Service
public class SignUp {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserReposistory userReposistory;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String createUser(SignUpUserRequestDto dto){
        if (userReposistory.existsByEmail(dto.getEmail())) {
            throw new EmailAlreadyExistsException("email is already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setName(dto.getName());

        userReposistory.save(user);
        
        return jwtService.generateToken(user.getEmail());
    }

    
    
}
