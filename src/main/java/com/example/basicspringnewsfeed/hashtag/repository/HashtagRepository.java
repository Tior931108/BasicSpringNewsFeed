package com.example.basicspringnewsfeed.hashtag.repository;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
    Optional<Hashtag> findByHashtagName(String hashtagName);

//    모든 테스트 완료후 문제 없을 때 구현
//    @Query("SELECT h FROM Hashtag h ORDER BY h.hashtagCount DESC LIMIT 10")
//    List<Hashtag> findTop10ByHashtagCount();
}
