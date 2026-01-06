package com.todo.backend.controller.auth;
import com.todo.backend.dto.auth.SignUpUserRequestDto;
import com.todo.backend.services.auth.SignIn;
import com.todo.backend.services.auth.SignUp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.backend.dto.auth.SignInUserRequestDto;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class authController {

    private final SignIn signIn;

    @Autowired
    private SignUp signUp;

    authController(SignIn signIn) {
        this.signIn = signIn;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> login(@Valid @RequestBody SignInUserRequestDto dto) {
        return ResponseEntity.ok(signIn.loginUser(dto));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpUserRequestDto dto) {
        return ResponseEntity.ok(signUp.createUser(dto));
    }
    
}
