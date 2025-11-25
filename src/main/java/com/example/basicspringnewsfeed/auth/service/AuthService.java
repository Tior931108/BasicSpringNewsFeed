package com.example.basicspringnewsfeed.auth.service;


import com.example.basicspringnewsfeed.auth.dto.*;
import com.example.basicspringnewsfeed.auth.dto.*;
import com.example.basicspringnewsfeed.auth.dto.*;

import com.example.basicspringnewsfeed.common.security.CustomUserDetails;
import com.example.basicspringnewsfeed.common.security.JwtTokenProvider;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;

    // POST
    @Transactional
    public UserResponse signUp(SignupRequest request){

        validateEmail(request);

        String encodedPassword=passwordEncoder.encode(request.getPassword());

        User user = authMapper.ofSignUp(encodedPassword, request);

        userRepository.save(user);

        return authMapper.toUserResponse(user);


    }

    // POST
    @Transactional
    public LoginResponse login(LoginRequest request){

        User user= findUserByEmailOrThrow(request);

        validatePassword(request, user);

        CustomUserDetails principal = new CustomUserDetails(user);

        String token=jwtTokenProvider.generateToken(principal);

        UserResponse response = authMapper.toUserResponse(user);

        return new LoginResponse(token, response);
    }

    // 정리용 헬퍼 메서드
    private User findUserByEmailOrThrow(LoginRequest request){
        return userRepository.findByEmail(request.getEmail()) // 검사 + 대입
                .orElseThrow(() -> new UsernameNotFoundException("1"));
    }
    private void validateEmail(SignupRequest request){
        if(userRepository.existsByEmail(request.getEmail())){ // 검사
            throw new UsernameNotFoundException("1");
        }
    }
    private void validatePassword(LoginRequest request, User user){
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){ // matches로 검증
            throw new UsernameNotFoundException("1");
        }
    }
}