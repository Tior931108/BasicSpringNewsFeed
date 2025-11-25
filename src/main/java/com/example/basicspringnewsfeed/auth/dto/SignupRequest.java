package com.example.basicspringnewsfeed.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED) // JPA JSON -> parameter 비어있는 생성자 생성
public class SignupRequest {

    @NotBlank
    @Email
    @Size(max=100)
    private String email;

    @NotBlank @Size(max=30)
    private String nickname;

    @NotBlank @Size(min=8, max=100)
    private String password;

    public SignupRequest(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}
