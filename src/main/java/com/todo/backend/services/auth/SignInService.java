package com.todo.backend.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.backend.dto.auth.SignInUserRequestDto;
import com.todo.backend.entity.UserEntity;
import com.todo.backend.exception.impl.EmailNotFoundException;
import com.todo.backend.exception.impl.PasswordNotFoundException;
import com.todo.backend.reposistory.UserReposistory;


@Service
public class SignInService {

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserReposistory userReposistory;

    SignInService(PasswordEncoder passwordEncoder, JwtService jwtService, UserReposistory userReposistory) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userReposistory = userReposistory;
    }

    public String loginUser(SignInUserRequestDto signInUserRequestDto){

        // fetch the user
        UserEntity user = userReposistory.findByEmail(signInUserRequestDto.getEmail()).orElseThrow(()-> new EmailNotFoundException("Email not found"));

        // verify password
        if (!authenticate(signInUserRequestDto.getPassword(), user.getPassword())) {
            throw new PasswordNotFoundException("Invalid password");
        }

        return jwtService.generateToken(user.getEmail());
    }

    private boolean authenticate(String rawPassword,String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

}
