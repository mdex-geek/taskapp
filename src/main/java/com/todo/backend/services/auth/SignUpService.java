package com.todo.backend.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.backend.dto.auth.SignUpUserRequestDto;
import com.todo.backend.entity.UserEntity;
import com.todo.backend.exception.impl.EmailAlreadyExistsException;
import com.todo.backend.reposistory.UserReposistory;

@Service
public class SignUpService {

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

//        due to race-condition at same time
        try{
            return jwtService.generateToken(userReposistory.save(user).getEmail());
        }catch (DataIntegrityViolationException ex){
            throw new EmailAlreadyExistsException("email is already exists");
        }

    }

    
    
}
