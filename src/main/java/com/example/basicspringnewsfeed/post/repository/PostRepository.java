package com.example.basicspringnewsfeed.post.repository;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    // 삭제되지 않은 게시글만 페이징 조회
    Page<Post> findByIsDeleteOrderByUpdatedAtDesc(IsDelete isDelete, Pageable pageable);
}
