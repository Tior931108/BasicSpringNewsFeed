package com.example.basicspringnewsfeed.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class UserProfileUpdateRequest {
    @NotBlank @Size(max=50)
    private String nickname;
    @Size(max=254)
    private String profileImageUrl;
    @Size(max=200)
    private String introduce;

    public UserProfileUpdateRequest(String nickname, String profileImageUrl, String introduce){
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.introduce = introduce;
    }
}
