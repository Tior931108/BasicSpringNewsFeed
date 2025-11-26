package com.example.basicspringnewsfeed.post.controller;

import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.post.dto.PostCreateRequestDto;
import com.example.basicspringnewsfeed.post.dto.PostResponseDto;
import com.example.basicspringnewsfeed.post.dto.PostUpdateRequestDto;
import com.example.basicspringnewsfeed.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @ModelAttribute PostCreateRequestDto request) {

//        System.out.println("=== Controller 진입 ===");
//        System.out.println("userId: " + request.getUserId());
//        System.out.println("title: " + request.getTitle());
//        System.out.println("content: " + request.getContent());
//        System.out.println("images: " + request.getImages());
//        System.out.println("images size: " + (request.getImages() != null ? request.getImages().size() : "null"));
//
//        if (request.getImages() != null) {
//            for (int i = 0; i < request.getImages().size(); i++) {
//                MultipartFile file = request.getImages().get(i);
//                System.out.println("File " + i + ":");
//                System.out.println("  - 파일명: " + file.getOriginalFilename());
//                System.out.println("  - 크기: " + file.getSize());
//                System.out.println("  - 비어있음?: " + file.isEmpty());
//                System.out.println("  - ContentType: " + file.getContentType());
//            }
//        }

        PostResponseDto response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 전체 조회 (페이징)
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPosts(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        Page<PostResponseDto> response = postService.getPosts(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 댓글 많은순 TOP3 조회
    @GetMapping("/top")
    public ResponseEntity<List<PostResponseDto>> getTop3Posts() {
        List<PostResponseDto> posts = postService.getTop3PostsByCommentCount();
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }


    // 게시글 수정
    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long postId,
            @Valid @ModelAttribute PostUpdateRequestDto request,
            @AuthUser CurrentUser currentUser) {

        PostResponseDto response = postService.updatePost(postId, request, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthUser CurrentUser currentUser) {

        postService.deletePost(postId, currentUser);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
