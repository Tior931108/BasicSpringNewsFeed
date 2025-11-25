package com.example.basicspringnewsfeed.user.service;

import com.example.basicspringnewsfeed.user.dto.*;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly=true)
    public UserProfileResponse getProfile(Long currentUserId) {
        User user = getUserOrThrow(currentUserId);

        return userMapper.toUserProfile(user);
    }
    @Transactional
    public UserProfileResponse updateProfile(Long currentUserId, UserProfileUpdateRequest request){
        User user = getUserOrThrow(currentUserId);
        user.updateProfile(request.getNickname(), request.getProfileImageUrl(), request.getIntroduce());

        return userMapper.toUserProfile(user);
    }
    @Transactional
    public void changePassword(Long currentUserId, ChangePasswordRequest request) {

        User user = getUserOrThrow(currentUserId);

        validatePassword(request.getCurrentPassword(), user);

        String encoded = passwordEncoder.encode(request.getNewPassword());

        user.changePassword(encoded);
    }
    @Transactional
    public void changeEmail(Long currentUserId, ChangeEmailRequest request) {

        User user = getUserOrThrow(currentUserId);

        validatePassword(request.getCurrentPassword(), user);

        validateEmail(request.getNewEmail(), user.getUserId());

        user.changeEmail(request.getNewEmail());
    }
    @Transactional
    public void deleteUser(Long currentUserId, DeleteUserRequest request){
        User user=getUserOrThrow(currentUserId);
        validatePassword(request.getPassword(), user);
        userRepository.deleteById(user.getUserId());
    }


    private User getUserOrThrow(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("1"));
    }
    private void validatePassword(String currentPassword, User user){
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){ // matches로 검증
            throw new UsernameNotFoundException("1");
        }
    }
    private void validateEmail(String newEmail, Long currentUserId){
        if(userRepository.existsByEmailAndUserIdNot(newEmail, currentUserId)){ // 검사
            throw new UsernameNotFoundException("1");
        }
    }
}