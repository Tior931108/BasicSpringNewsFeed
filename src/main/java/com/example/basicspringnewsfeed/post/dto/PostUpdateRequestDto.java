package com.example.basicspringnewsfeed.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostUpdateRequestDto {

    private Long userId;
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(max = 100, message = "제목은 100자 이하로 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 400, message = "내용은 400자 이하로 입력해주세요.")
    private String content;

    // 해시태그 리스트
//    private List<String> hashtags;

    // 새로 추가할 이미지들
    private List<MultipartFile> newImages;

    // 삭제할 이미지 ID 리스트
    private List<Long> deleteImageIds;
}
