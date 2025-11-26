package com.example.basicspringnewsfeed.post.repository;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    // 삭제되지 않은 게시글만 페이징 조회
    Page<Post> findByIsDeleteOrderByUpdatedAtDesc(IsDelete isDelete, Pageable pageable);

    // 댓글 많은 순 TOP3 조회 (댓글 수 같으면 수정일자 내림차순)
    @Query("SELECT p FROM Post p " +
            "WHERE p.isDelete = :isDelete " +
            "ORDER BY p.commentCount DESC, p.updatedAt DESC")
    List<Post> findTop3ByIsDeleteOrderByCommentCountDescUpdatedAtDesc(IsDelete isDelete, Pageable topThree);
}
