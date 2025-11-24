package com.example.basicspringnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class ChangePasswordRequest {
    @NotBlank @Size(max=254)
    private String currentPassword;
    @NotBlank @Size(max=254)
    private String newPassword;

    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }
}
