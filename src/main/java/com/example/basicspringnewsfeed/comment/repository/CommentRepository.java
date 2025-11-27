package com.example.basicspringnewsfeed.comment.repository;

import com.example.basicspringnewsfeed.comment.entity.Comment;
import com.example.basicspringnewsfeed.common.entity.IsDelete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 단 건 조회 비활성화 데이터 조회 제외
    Optional<Comment> findByCommentIdAndIsDelete(Long commentId, IsDelete isDelete);

    // 다 건 조회 비활성화 데이터 조회 제외
    List<Comment> findAllByIsDelete(IsDelete isDelete);

}
