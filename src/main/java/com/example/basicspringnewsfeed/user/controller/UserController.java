package com.example.basicspringnewsfeed.user.controller;

import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.user.dto.*;

import com.example.basicspringnewsfeed.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/me")
public class UserController {
    private final UserService userService;

    // 내 정보
    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthUser CurrentUser currentUser) {
        UserProfileResponse body = userService.getProfile(currentUser.id());
        return ResponseEntity.ok(body);
    }
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@AuthUser CurrentUser currentUser, @Valid @RequestBody ChangePasswordRequest request) {
        userService.changePassword(currentUser.id(), request);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/email")
    public ResponseEntity<Void> changeEmail(@AuthUser CurrentUser currentUser, @Valid @RequestBody ChangeEmailRequest request) {
        userService.changeEmail(currentUser.id(), request);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@AuthUser CurrentUser currentUser, @Valid @RequestBody UserProfileUpdateRequest request) {
        UserProfileResponse body = userService.updateProfile(currentUser.id(), request);
        return ResponseEntity.ok(body);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@AuthUser CurrentUser currentUser, @Valid @RequestBody DeleteUserRequest request){
        userService.deleteUser(currentUser.id(), request);
        return ResponseEntity.noContent().build(); // 204
    }
}