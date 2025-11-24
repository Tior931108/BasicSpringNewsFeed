package com.example.basicspringnewsfeed.story.repository;

import com.example.basicspringnewsfeed.common.entity.IsDelete;
import com.example.basicspringnewsfeed.story.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoryRepository extends JpaRepository<Story, Long> {


    List<Story> findByIdAndIsDeleteOrderByCreatedAtDesc(IsDelete isDelete);


    Optional<Story> findByStoryIdAndIsDelete(Long storyId, IsDelete isDelete);
}
