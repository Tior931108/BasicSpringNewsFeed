package com.example.basicspringnewsfeed.post.service;

import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.hashtag.service.HashtagService;
import com.example.basicspringnewsfeed.hashtagandpost.entity.HashtagPost;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.image.service.ImageService;
import com.example.basicspringnewsfeed.post.dto.PostCreateRequestDto;
import com.example.basicspringnewsfeed.post.dto.PostDetailResponseDto;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final HashtagService hashtagService;

    // 게시글 생성
    @Transactional
    public PostResponseDto createPost(PostCreateRequestDto request) {
        // 유저 확인
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        // 게시글 생성
        Post post = new Post(user, request.getTitle(), request.getContent());

        // 아미지 업로드 및 저장
        List<Image> images = new ArrayList<>();
        if (request.getImages() != null && !request.getImages().isEmpty()) {
            images = imageService.uploadImages(post, request.getImages());
        }

        // 게시글 저장
        postRepository.save(post);

        /*
        * 11.26 성주연
        * 순서 바꾸면안됨!
        * Post가 DB에 저장되지 않으면:
        * Post의 ID가 없음 (null)
        * HashtagPost 저장 시 foreign key 오류 발생
         */
        // 해시태그 추가
        if (request.getHashtags() != null && !request.getHashtags().isEmpty()) {
            hashtagService.addHashtagsToPost(post, request.getHashtags());
        }

        // 응답 생성 - 해시태그 추가
        List<Hashtag> hashtags = post.getPostHashtags().stream()
                .map(HashtagPost::getHashtag)
                .toList();

        // 4. 응답 생성
        return new PostResponseDto(post, images, hashtags);
    }

    // 게시글 전체 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPosts(Pageable pageable) {
        // 삭제되지 않은 게시글만 조회
        Page<Post> posts = postRepository.findByIsDeleteOrderByUpdatedAtDesc(
                IsDelete.N,
                pageable
        );

        // 11.26 성주연 - 해시태그
        // Post를 PostResponseDto로 변환
        // Post 객체로 비교하기 때문에 Post 자체를 전달
        // JPA에서 Post 객체의 ID로 자동으로 비교하므로 추가 데이터 조회 없음
        return posts.map(post -> {
            List<Hashtag> hashtags = hashtagService.getHashtagsByPost(post);
            List<Image> images = imageService.getImagesByPostId(post.getPostId());
            return new PostResponseDto(post,images,hashtags);
        });
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long postId, @Valid PostUpdateRequestDto request, CurrentUser currentUser) {
        // 게시글 조회 및 권한 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        // 수정 권한 체크
//        if (!post.getUser().getUserId().equals(currentUser.id())) {
//            throw new IllegalStateException("수정 권한이 없습니다.");
//        }

        // 게시글 내용 수정
        post.update(request.getTitle(), request.getContent());

        // 해시태그 업데이트 (있으면)
        if (request.getHashtags() != null && !request.getHashtags().isEmpty()) {
            hashtagService.updateHashtagsForPost(post, request.getHashtags());
        }

        // 이미지 삭제 : null이 아닌 경우나 비어있지 않은 경우
        if (request.getDeleteImageIds() != null && !request.getDeleteImageIds().isEmpty()) {
            imageService.deleteImages(request.getDeleteImageIds());
        }

        // 새 이미지 추가
        if (request.getNewImages() != null && !request.getNewImages().isEmpty()) {
            imageService.uploadImages(post, request.getNewImages());
        }

        // 응답 생성
        List<Hashtag> hashtags = hashtagService.getHashtagsByPost(post);
        List<Image> images = imageService.getImagesByPost(post);
        return new PostResponseDto(post, images,hashtags);
    }

    // 게시글 삭제
    @Transactional
    public void deletePost(Long postId, CurrentUser currentUser) {
        // 게시글 조회 및 권한 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));

        // 삭제 권한 체크
//        if (!post.getUser().getUserId().equals(currentUser.id())) {
//            throw new IllegalStateException("삭제 권한이 없습니다.");
//        }

        // 해시태그 관계 삭제 및 카운트 감소
        hashtagService.deleteHashtagsForPost(post);

        // 이미지 파일 삭제 (물리적 파일 + DB 레코드)
        imageService.deleteAllImagesByPost(post);

        // 게시글 삭제 (삭제 여부 변경)
        post.updateIsDelete(IsDelete.Y);
    }

    /**
     * 게시글 상세 조회
     * @param postId 게시글 ID
     * @return 게시글 상세 정보 (해시태그, 이미지, 댓글수, 좋아요수 등)
     */
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findByIdWithHashtagsAndImages(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        return convertToDetailDto(post);
    }

    //현재 게시글 상세조회로 받고있음 나중에 게시글만 전체조회하는 api받으면 그거 활용할 예정
    /**
     * 특정 해시태그로 게시글 조회 (페이징)
     * @param hashtag 해시태그명
     * @param page 페이지 번호
     * @param size 페이지 크기
     * @return 해당 해시태그가 있는 게시글들
     * 전체글조회로 조회 (댓글X)
     */


    public Page<PostResponseDto> getPostsByHashtag(String hashtag, int page, int size) {
        if (hashtag == null || hashtag.trim().isEmpty()) {
            throw new CustomException(ErrorCode.INVALID_HASHTAG);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Post> posts = postRepository.findByHashtagNameWithPosts(hashtag, pageable);

        if (posts.isEmpty()) {
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        }

        return posts.map(post -> {
            List<Image> images = imageService.getImagesByPostId(post.getPostId());
            List<Hashtag> hashtags = post.getPostHashtags().stream()
                    .map(HashtagPost::getHashtag)
                    .toList();
            return new PostResponseDto(post, images, hashtags);
        });
    }

    /**
     * Post를 PostDetailResponseDto로 변환 (민감한 정보 보호)
     * Post 엔티티에는 필요 없는 필드도 있을 수 있음
     * 응답에 보낼 필드만 선택 가능
     * 엔티티와 API 분리
     */
    private PostDetailResponseDto convertToDetailDto(Post post) {
        List<Hashtag> hashtags = post.getPostHashtags().stream()
                .map(HashtagPost::getHashtag)
                .toList();

        List<Image> images = post.getImages();
        List<Comment> comments = post.getComments();

        return PostDetailResponseDto.from(post, hashtags, images, comments);
    }
}
