package com.example.basicspringnewsfeed.userpostlike.service;

import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.post.repository.PostRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import com.example.basicspringnewsfeed.userpostlike.dto.res.UserPostsLikeResponseDto;
import com.example.basicspringnewsfeed.userpostlike.entity.UserPostLike;
import com.example.basicspringnewsfeed.userpostlike.repository.UserPostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserPostsLikeService {
    private final UserPostLikeRepository userPostLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public UserPostsLikeResponseDto toggleLike(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 내 글에는 좋아요 금지
        if (post.getUser().getUserId().equals(userId)) {
            throw new CustomException(ErrorCode.SELF_LIKE_FORBIDDEN);
        }

        /*
         .existsByUserAndPost(user, post) - 특정 사용자가 특정 게시글에 좋아요를 눌렀는지 확인
         반환값: `true` (좋아요 있음) 또는 `false` (좋아요 없음)
         `isLiked` - 그 결과를 저장
         */
        // 좋아요 토글
        boolean isLiked = userPostLikeRepository.existsByUserAndPost(user, post);

        if (isLiked) {
            // Unlike
            UserPostLike like = userPostLikeRepository.findByUserAndPost(user, post)
                    .orElseThrow(() -> new IllegalStateException("좋아요 정보를 찾을 수 없습니다."));
            userPostLikeRepository.delete(like);
            post.decreaseLikeCount();
            return new UserPostsLikeResponseDto(false, post.getLikedCount(), "좋아요 취소 완료");
        } else {
            // Like
            userPostLikeRepository.save(new UserPostLike(user, post));
            post.increaseLikeCount();
            return new UserPostsLikeResponseDto(true, post.getLikedCount(), "좋아요 완료");
        }
    }
}
