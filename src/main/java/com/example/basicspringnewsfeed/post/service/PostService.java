package com.example.basicspringnewsfeed.post.service;

import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.image.repository.ImageRepository;
import com.example.basicspringnewsfeed.image.service.ImageService;
import com.example.basicspringnewsfeed.post.dto.PostCreateRequestDto;
import com.example.basicspringnewsfeed.post.dto.PostResponseDto;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.post.repository.PostRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto request) {
        // 1. 유저 확인
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 2. 게시글 생성
        Post post = new Post(user, request.getTitle(), request.getContent());

        // 3. 아미지 업로드 및 저장
        List<Image> images = new ArrayList<>();
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            images = imageService.uploadImages(post, request.getImages());
        }

        // 4. 게시글 저장
        postRepository.save(post);

        // 4. 응답 생성
        return new PostResponseDto(post, images);
    }


}
