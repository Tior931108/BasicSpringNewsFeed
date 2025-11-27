package com.example.basicspringnewsfeed.comment.service;

import com.example.basicspringnewsfeed.comment.dto.request.CommentCreateRequest;
import com.example.basicspringnewsfeed.comment.dto.response.CommentCreateResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentGetResponse;
import com.example.basicspringnewsfeed.comment.dto.response.CommentUpdateResponse;
import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.comment.repository.CommentRepository;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.security.CurrentUser;
import com.example.basicspringnewsfeed.post.entity.Post;
import com.example.basicspringnewsfeed.post.repository.PostRepository;
import com.example.basicspringnewsfeed.user.entity.User;
import com.example.basicspringnewsfeed.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //생성
    @Transactional
    public CommentCreateResponse save(Long postId, Long userId, CommentCreateRequest request) {
        // user 존재하지 않을 때 예외처리
        User user = userRepository.findById(userId).orElseThrow(
                // 404에러
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );

        //post 존재하지 않을 때 예외처리
        Post post = postRepository.findById(postId).orElseThrow(
                // 404에러
                () -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment comment = new Comment(
                post,
                user,
                request.getContent()

        );

        //댓글 저장
        Comment commentSave = commentRepository.save(comment);

        //댓글 카운트 (post 클래스에서 호출)
        post.increaseCommentCount();

        return new CommentCreateResponse(
                commentSave.getCommentId(),
                commentSave.getUser().getUserId(),
                commentSave.getUser().getNickname(),
                commentSave.getContent(),
                commentSave.getCreatedAt());
    }

    //단 건 조회
    @Transactional(readOnly = false)
    public CommentGetResponse commentOneGet(@PathVariable Long commentId, CurrentUser currentUser, Long postId) {
        //유저 정보
        User user = userRepository.findById(currentUser.id())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

//         단 건 조회 비활성화 데이터 조회 제외 코드
        Comment comment = commentRepository.findByCommentIdAndIsDelete(commentId, IsDelete.N).orElseThrow(
                // 예외처리 변경할 부분
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

        {
            return new CommentGetResponse(
                    comment.getCommentId(),
                    comment.getUser().getUserId(),
                    comment.getUser().getNickname(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
        }
    }

    //다 건 조회
    @Transactional(readOnly = false)
    public List<CommentGetResponse> commentAllGet() {
        //삭제(비활성)된 데이터는 조회에서 제외
        List<Comment> comments = commentRepository.findAllByIsDelete(IsDelete.N);

        // 반환할 리스트 준비
        List<CommentGetResponse> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentGetResponse dto = new CommentGetResponse(
                    comment.getCommentId(),
                    comment.getUser().getUserId(),
                    comment.getUser().getNickname(),
                    comment.getContent(),
                    comment.getCreatedAt()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //수정
    @Transactional
    public CommentUpdateResponse commentUpdate(Long commentId, CommentCreateRequest request, CurrentUser currentUser) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                // 댓글이 없는 경우
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );

//        //댓글 권한 없을 때 에러 발생 403에러
//        if (!comment.getUser().getUserId().equals(commentId)){
//            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
//        }

        if (!comment.getUser().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.ONLY_OWNER_UPDATE);
        }

        //  댓글 내용 수정
        comment.commentUpdate(request.getContent());

        //수정 시 반환 내용
        return new CommentUpdateResponse(
                comment.getCommentId(),
                comment.getUser().getUserId(),
                comment.getUser().getNickname(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    //삭제
    @Transactional
    public void commentDelete(Long commentId, CurrentUser currentUser
    ) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                //404에러
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
        // 댓글 권한 없을 때 에러 발생 -> 403에러
        if (!comment.getUser().getUserId().equals(currentUser.id())) {
            throw new CustomException(ErrorCode.COMMENT_FORBIDDEN);
        }

//        if (!comment.getUser().getUserId().equals(currentUser.id())) {
//            throw new CustomException(ErrorCode.ONLY_OWNER_UPDATE);
//        }

        //IsDelete 겂을 No -> Yes(비활성)로 변경
        comment.updateIsDelete(IsDelete.Y);

        //post 값을 comment로 불러옴
        Post post = comment.getPost();

        //post에서 불러온 기능으로 함수 카운터를 마이너스함
        post.decreaseCommentCount();
    }
}

