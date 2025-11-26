package com.example.basicspringnewsfeed.hashtag.service;

import com.example.basicspringnewsfeed.hashtag.entity.Hashtag;
import com.example.basicspringnewsfeed.hashtag.repository.HashtagRepository;
import com.example.basicspringnewsfeed.hashtagandpost.entity.HashtagPost;
import com.example.basicspringnewsfeed.hashtagandpost.repository.HashtagPostRepository;
import com.example.basicspringnewsfeed.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashtagPostRepository hashtagPostRepository;

    /**
     * 게시글에 해시태그 추가
     * 받은 해시태그 리스트로 처리해서
     * 각 해시태그가 db에 있는지 확인
     * -> 있으면 사용, 없으면 새로 생성
     * HashtagPost로 Post와 연결
     * 해시카운트 증가
     */
    public void addHashtagsToPost(Post post, List<String> hashtagNames) {
        if (hashtagNames == null || hashtagNames.isEmpty()) {
            return;
        }

        for (String name : hashtagNames) {
            String trimmedName = name.trim();

            // 해시태그 존재 여부 확인, 없으면 생성
            Hashtag hashtag = hashtagRepository.findByHashtagName(trimmedName)
                    .orElseGet(() -> {
                        Hashtag newHashtag = new Hashtag(trimmedName);
                        return hashtagRepository.save(newHashtag);
                    });

            // HashtagPost 생성
            HashtagPost hashtagPost = new HashtagPost(hashtag, post);
            hashtagPostRepository.save(hashtagPost);

            // 게시글에 연결
            post.getPostHashtags().add(hashtagPost);  // ← 여기 추가!

            // 해시태그 카운트 증가
            hashtag.increaseCount();
            hashtagRepository.save(hashtag);
        }
    }

    /**
     * 게시글의 해시태그 수정 (기존 태그 삭제 후 새로 추가)
     * 기존 해시태그 삭제(카운트 감소)
     * 새 해시태그 추가(카운트 증가)
     */
    public void updateHashtagsForPost(Post post, List<String> hashtagNames) {
        // 기존 해시태그 삭제 및 카운트 감소
        List<HashtagPost> existingTags = hashtagPostRepository.findByPost(post);
        for (HashtagPost hashtagPost : existingTags) {
            hashtagPost.getHashtag().decreaseCount();
            hashtagRepository.save(hashtagPost.getHashtag());
            hashtagPostRepository.delete(hashtagPost);
        }

        // 새로운 해시태그 추가
        addHashtagsToPost(post, hashtagNames);
    }

    /**
     * 게시글의 모든 해시태그 삭제
     * 게시글 삭제 할 때 해시태그도 같이 삭제
     * 얘 없으면 해시태그 포스트에 남아있음(고아 데이터)
     */
    public void deleteHashtagsForPost(Post post) {
        List<HashtagPost> hashtagPosts = hashtagPostRepository.findByPost(post);

        for (HashtagPost hashtagPost : hashtagPosts) {
            hashtagPost.getHashtag().decreaseCount();
            hashtagRepository.save(hashtagPost.getHashtag());
        }

        hashtagPostRepository.deleteByPost(post);
    }

    /**
     * 특정 게시글의 해시태그 조회
     */
    @Transactional(readOnly = true)
    public List<Hashtag> getHashtagsByPost(Post post) {
        return hashtagPostRepository.findHashtagsByPost(post);
    }

//    /**
//     * 인기 해시태그 Top 10 조회
//     */
//    @Transactional(readOnly = true)
//    public List<Hashtag> getTop10Hashtags() {
//        return hashtagRepository.findTop10ByHashtagCount();
//    }
}
