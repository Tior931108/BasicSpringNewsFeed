package com.example.basicspringnewsfeed.auth.controller;

import com.example.basicspringnewsfeed.auth.dto.LoginRequest;
import com.example.basicspringnewsfeed.auth.dto.LoginResponse;
import com.example.basicspringnewsfeed.auth.dto.SignupRequest;
import com.example.basicspringnewsfeed.auth.dto.UserResponse;
import com.example.basicspringnewsfeed.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController{

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@Valid @RequestBody SignupRequest request){
        UserResponse body=authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PostMapping("/login") // token
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse body=authService.login(request);
        return ResponseEntity.ok(body);
    }

}