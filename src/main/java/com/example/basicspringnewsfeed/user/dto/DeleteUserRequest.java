package com.example.basicspringnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class DeleteUserRequest {
    @NotBlank @Size(max=254)
    private String password;

    public DeleteUserRequest(String password) {
        this.password = password;
    }
}
