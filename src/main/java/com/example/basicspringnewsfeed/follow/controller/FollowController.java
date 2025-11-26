package com.example.basicspringnewsfeed.follow.controller;

import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.follow.dto.FollowRequest;
import com.example.basicspringnewsfeed.follow.dto.FollowResponse;
import com.example.basicspringnewsfeed.follow.dto.FollowerInfo;
import com.example.basicspringnewsfeed.follow.dto.FollowingInfo;
import com.example.basicspringnewsfeed.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우 요청
    @PostMapping("/follows")
    public ResponseEntity<FollowResponse> follow(@RequestBody FollowRequest followRequest) {
        FollowResponse followResponse = followService.follow(followRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(followResponse);
    }

    // 팔로우 승인
    @PutMapping("/follows/approve/{followId}")
    public ResponseEntity<FollowResponse> approveFollow(@PathVariable Long followId, @AuthUser CurrentUser currentUser) {
        FollowResponse followResponse = followService.approveFollow(followId, currentUser);
        return ResponseEntity.ok(followResponse);
    }

    // 팔로우 거절
    @DeleteMapping("/follows/reject/{followId}")
    public ResponseEntity<FollowResponse> rejectFollow(@PathVariable Long followId, @AuthUser CurrentUser currentUser) {
        FollowResponse followResponse = followService.rejectFollow(followId, currentUser);
        return ResponseEntity.ok(followResponse);
    }


    // 팔로잉 삭제
    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<String> deleteFollow(@PathVariable Long followId, @AuthUser CurrentUser currentUser) {
        followService.deleteFollow(followId, currentUser);
        return ResponseEntity.ok("팔로우가 삭제되었습니다.");  // 삭제 문구 출력
    }


    // 팔로워 조회
    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowerInfo>> getFollowers(@PathVariable Long userId) {
        List<FollowerInfo> followers = followService.getFollowers(userId);
        return ResponseEntity.ok(followers);
    }


    // 팔로잉 조회
    @GetMapping("/followings/{userId}")
    public ResponseEntity<List<FollowingInfo>> getFollowing(@PathVariable Long userId) {
        List<FollowingInfo> followings = followService.getFollowing(userId);
        return ResponseEntity.ok(followings);
    }


}
