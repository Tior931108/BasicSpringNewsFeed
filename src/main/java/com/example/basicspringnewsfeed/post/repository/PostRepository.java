package com.example.basicspringnewsfeed.post.repository;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    // 삭제되지 않은 게시글만 페이징 조회
    Page<Post> findByIsDeleteOrderByUpdatedAtDesc(IsDelete isDelete, Pageable pageable);

    // 단일 게시글 상세 조회 (해시태그, 이미지, 댓글, 작성자 포함)
    @EntityGraph(attributePaths = {"postHashtags", "postHashtags.hashtag", "user", "images", "comments"})
    @Query("SELECT p FROM Post p WHERE p.postId = :postId AND p.isDelete = 'N'")
    Optional<Post> findByIdWithHashtagsAndImages(@Param("postId") Long postId);

    @Query(
            value = "SELECT DISTINCT p FROM Post p " +
                    "JOIN p.postHashtags ph " +
                    "WHERE ph.hashtag.hashtagName = :hashtagName AND p.isDelete = 'N' " +
                    "ORDER BY p.createdAt DESC",
            countQuery = "SELECT COUNT(DISTINCT p) FROM Post p " +
                    "JOIN p.postHashtags ph " +
                    "WHERE ph.hashtag.hashtagName = :hashtagName AND p.isDelete = 'N'"
    )
    @EntityGraph(attributePaths = {"postHashtags", "postHashtags.hashtag", "user", "images", "comments"})
    Page<Post> findByHashtagNameWithPosts(@Param("hashtagName") String hashtagName, Pageable pageable);

    // 댓글 많은 순 TOP3 조회 (댓글 수 같으면 수정일자 내림차순)
    @Query("SELECT p FROM Post p " +
            "WHERE p.isDelete = :isDelete " +
            "ORDER BY p.commentCount DESC, p.updatedAt DESC")
    List<Post> findTop3ByIsDeleteOrderByCommentCountDescUpdatedAtDesc(IsDelete isDelete, Pageable topThree);
}
