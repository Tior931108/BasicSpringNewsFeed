package com.example.basicspringnewsfeed.post.controller;

import com.example.basicspringnewsfeed.common.security.AuthUser;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.post.dto.PostCreateRequestDto;
import com.example.basicspringnewsfeed.post.dto.PostDetailResponseDto;
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

    /**
     * 성주연 11.26
     * 게시글 상세 조회 또는 해시태그 검색
     * hashtag 파라미터 없음: 게시글 상세 조회 (댓글, 이미지, 해시태그 모두 포함)
     * hashtag 파라미터 있음: 해당 해시태그 게시글들 검색 (페이징, 댓글 미포함)
     * <Object로 반환하는 이유>
     * 해시태그가 있을 때는 Page<PostResponseDto> 형태를 반환하고,
     * 해시태그가 없을 때는 PostDetailResponseDto 단건 데이터를 반환함.
     * 두 타입의 구조가 완전히 다르기 때문에 공통 부모 타입으로 묶기 어렵고,
     * 억지 캐스팅 시 런타임 오류가 발생할 수 있음.
     * 따라서 상황에 따라 다른 DTO 구조를 유연하게 반환하기 위해
     * ResponseEntity<Object>를 사용함.
     * @param postId 게시글 ID
     * @param hashtag 해시태그명 (선택)
     * @param page 페이지 번호 (hashtag 입력 시만 사용)
     * @param size 페이지 크기 (hashtag 입력 시만 사용)
     * @return 게시글 상세정보 또는 해시태그 게시글 목록
     */
    @GetMapping("/{postId}")
    public ResponseEntity<Object> getPost(
            @PathVariable Long postId,
            @RequestParam(required = false) String hashtag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // 해시태그 파라미터가 있으면 해시태그 검색
        if (hashtag != null && !hashtag.trim().isEmpty()) {
            Page<PostResponseDto> posts = postService.getPostsByHashtag(hashtag, page, size);
            return ResponseEntity.ok(posts);
        }

        // 해시태그 없으면 상세 조회
        PostDetailResponseDto post = postService.getPostDetail(postId);
        return ResponseEntity.ok(post);
    }

}
