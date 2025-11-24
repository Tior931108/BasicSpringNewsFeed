package com.example.basicspringnewsfeed.userpostlike.controller;

import com.example.basicspringnewsfeed.userpostlike.dto.req.LikeToggleRequestDto;
import com.example.basicspringnewsfeed.userpostlike.dto.res.UserPostsLikeResponseDto;
import com.example.basicspringnewsfeed.userpostlike.service.UserPostsLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class UserPostsLikeController {
    private final UserPostsLikeService userPostsLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<UserPostsLikeResponseDto> toggleLike(
            @PathVariable Long postId,
            @RequestBody LikeToggleRequestDto request) {

        UserPostsLikeResponseDto response = userPostsLikeService.toggleLike(postId, request.userId());
        return ResponseEntity.ok(response);
    }
}
