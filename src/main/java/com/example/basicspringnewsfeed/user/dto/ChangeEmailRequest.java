package com.example.basicspringnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class ChangeEmailRequest {
    @NotBlank @Size(max=254)
    private String currentPassword;
    @NotBlank @Size(max=50)
    private String newEmail;

    public ChangeEmailRequest(String currentPassword, String newEmail) {
        this.currentPassword = currentPassword;
        this.newEmail = newEmail;
    }

}
