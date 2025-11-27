package com.example.basicspringnewsfeed.follow.service;


import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.follow.dto.FollowRequest;
import com.example.basicspringnewsfeed.follow.dto.FollowResponse;
import com.example.basicspringnewsfeed.follow.dto.FollowerInfo;
import com.example.basicspringnewsfeed.follow.dto.FollowingInfo;
import com.example.basicspringnewsfeed.follow.entity.Follow;
import com.example.basicspringnewsfeed.follow.repository.FollowRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    // 팔로우 요청
    @Transactional
    public FollowResponse follow(FollowRequest followRequest) {
        // User 조회하기
        User follower = userRepository.findById(followRequest.getFollowerId()).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOWER_NOT_FOUND)  // Follower 실제 있는 User인지 검증
        );
        User following = userRepository.findById(followRequest.getFollowingId()).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOWING_NOT_FOUND)  // Following 실제 있는 User인지 검증
        );

        // 이미 받은 팔로우 요청인지 확인하기
        if(followRepository.existsByFollowerIdAndFollowingId(follower, following)) {
            throw new CustomException(ErrorCode.FOLLOW_ALREADY_EXIST);   // 이미 팔로우를 신청한 관계인지 확인하기
        }

        // 팔로우 요청 생성하기
        Follow follow = new Follow(follower, following);
        follow = followRepository.save(follow);  // 차단 기록 DB에 저장
        followRepository.save(follow);
        return new FollowResponse(
                follow.getFollowId(),
                follow.getFollowerId().getUserId(),
                follow.getFollowingId().getUserId(),
                follow.getIsFollow()
        );
    }


    // 팔로우 승인
    @Transactional
    public FollowResponse approveFollow(Long followId, CurrentUser currentUser) {
        Follow follow = followRepository.findById(followId).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND)  // 없는 팔로우 예외처리
        );
        // 팔로우 승인 권한 예외처리
        if (!follow.getFollowerId().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.FOLLOWER_UNAUTHORIZED_ACCESS);
        }

        follow.updateAccepted(); // 상태 변경
        return new FollowResponse(
                follow.getFollowId(),
                follow.getFollowerId().getUserId(),
                follow.getFollowingId().getUserId(),
                follow.getIsFollow()
        );
    }


    // 팔로우 거절
    @Transactional
    public FollowResponse rejectFollow(Long followId, CurrentUser currentUser) {
        Follow follow = followRepository.findById(followId).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND)  // 없는 팔로우 예외처리
        );
        // 팔로우 거절 권한 예외처리
        if (!follow.getFollowerId().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.FOLLOWER_UNAUTHORIZED_ACCESS);
        }

        follow.updateRejected();

        return new FollowResponse(
                follow.getFollowId(),
                follow.getFollowerId().getUserId(),
                follow.getFollowingId().getUserId(),
                follow.getIsFollow()
        );
    }


    // 팔로우 삭제
    @Transactional
    public void deleteFollow(Long followId, CurrentUser currentUser) {
        Follow follow = followRepository.findById(followId).orElseThrow(
                () -> new CustomException(ErrorCode.FOLLOW_NOT_FOUND)   // 없는 팔로우 예외처리
        );
        // 팔로우 삭제 권한 예외처리
        if (!follow.getFollowerId().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.FOLLOW_UNAUTHORIZED_ACCESS);
        }
        followRepository.delete(follow);
    }


    // 팔로워 조회
    @Transactional(readOnly = true)
    public List<FollowerInfo> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));  // 조회하려는 User 존재하지 않음

        // user를 팔로우하는 Follow 목록
        List<Follow> followers = followRepository.findByFollowingId(user);

        // 반환 DTO 리스트
        List<FollowerInfo> followerdtos = new ArrayList<>();
        for (Follow follow : followers) {
            FollowerInfo dto = new FollowerInfo(
                    follow.getFollowerId().getUserId()   // 팔로워 ID만
            );
            followerdtos.add(dto);
        }
        return followerdtos;
    }


    // 팔로잉 조회
    @Transactional(readOnly = true)
    public List<FollowingInfo> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));  // 조회하려는 User 존재하지 않음

        // user가 팔로우하는 Follow 엔티티 목록
        List<Follow> followings = followRepository.findByFollowerId(user);

        // 반환 DTO 리스트
        List<FollowingInfo> followingdtos = new ArrayList<>();
        for (Follow follow : followings) {
            FollowingInfo dto = new FollowingInfo(
                    follow.getFollowingId().getUserId()  // 팔로잉 ID만
            );
            followingdtos.add(dto);
        }
        return followingdtos;
    }
}
