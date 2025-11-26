package com.example.basicspringnewsfeed.post.service;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.image.repository.ImageRepository;
import com.example.basicspringnewsfeed.image.service.ImageService;
import com.example.basicspringnewsfeed.post.dto.PostCreateRequestDto;
import com.example.basicspringnewsfeed.post.dto.PostResponseDto;
import com.example.basicspringnewsfeed.post.dto.PostUpdateRequestDto;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.post.repository.PostRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        // 유저 확인
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 해시태그 추가

        // 게시글 생성
        Post post = new Post(user, request.getTitle(), request.getContent());
        // 게시글 저장
        postRepository.save(post);

        // 아미지 업로드 및 저장
        List<Image> images = null;
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            images = imageService.uploadImages(post, request.getImages());
        }

        // 응답 생성 : 해시태그 추가 예정
        return new PostResponseDto(post, Objects.requireNonNull(images));
    }

    // 게시글 전체 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(Pageable pageable) {
        // 삭제되지 않은 게시글만 조회
        Page<Post> posts = postRepository.findByIsDeleteOrderByUpdatedAtDesc(
                IsDelete.N,
                pageable
        );

        // Post를 PostResponseDto로 변환
        return posts.map(post -> {
//            List<Hashtag> hashtags = hashtagService.getHashtagsByPost(post.getPostId());
            List<Image> images = imageService.getImagesByPostId(post.getPostId());
            return new PostResponseDto(post,images);
        });
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, @Valid PostUpdateRequestDto request, CurrentUser currentUser) {
        // 게시글 조회 및 권한 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 수정 권한 체크
        if (!post.getUser().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_UPDATE);
        }

        // 게시글 내용 수정
        post.update(request.getTitle(), request.getContent());

        // 해시태그 업데이트
//        if (request.getHashtags() != null) {
//            hashtagService.updateHashtagsForPost(postId, request.getHashtags());
//        }

        // 이미지 삭제 : null이 아닌 경우나 비어있지 않은 경우
        if (request.getDeleteImageIds() != null && !request.getDeleteImageIds().isEmpty()) {
            imageService.deleteImages(request.getDeleteImageIds());
        }

        // 새 이미지 추가
        if (request.getNewImages() != null && !request.getNewImages().isEmpty()) {
            imageService.uploadImages(post, request.getNewImages());
        }

        // 응답 생성
//        List<Hashtag> hashtags = hashtagService.getHashtagsByPost(postId);
        List<Image> images = imageService.getImagesByPost(post);
        return new PostResponseDto(post, images);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, CurrentUser currentUser) {
        // 게시글 조회 및 권한 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 삭제 권한 체크
        if (!post.getUser().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_DELETE);
        }

        // 해시태그 관계 삭제 및 카운트 감소
//        hashtagAndPostService.removeHashtagsFromPost(postId);

        // 이미지 파일 삭제 (물리적 파일 + DB 레코드)
        imageService.deleteAllImagesByPost(post);

        // 게시글 삭제 (삭제 여부 변경)
        post.updateIsDelete(IsDelete.Y);
    }

    // 댓글 많은 순 TOP3
    @Transactional(readOnly = true)
    public List<PostResponseDto> getTop3PostsByCommentCount() {

        Pageable topThree = PageRequest.of(0, 3);

        List<Post> posts = postRepository
                .findTop3ByIsDeleteOrderByCommentCountDescUpdatedAtDesc(IsDelete.N, topThree);

        return posts.stream()
                .map(post -> {
//                    List<Hashtag> hashtags = hashtagService.getHashtagsByPost(post.getPostId());
                    List<Image> images = imageService.getImagesByPostId(post.getPostId());
                    return new PostResponseDto(post, images);
                })
                .toList();
    }
}
