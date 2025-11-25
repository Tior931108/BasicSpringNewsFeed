package com.example.basicspringnewsfeed.image.service;

import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.image.repository.ImageRepository;
import com.example.basicspringnewsfeed.post.entity.Post;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    // application.properties에 설정
    @Value("${file.upload.path}")
    private String uploadPath;

    // 이미지 업로드
    public List<Image> uploadImages(Post post, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : files) {
            // 파일 검증
            validateImageFile(file);

            // 파일 저장
            String fileName = generateFileName(Objects.requireNonNull(file.getOriginalFilename()));
            String filePath = saveFile(file, fileName);

            // DB에 저장
            Image image = new Image(post, fileName, filePath);
            imageRepository.save(image);
            images.add(image);
        }

        return images;
    }

    // 이미지 삭제 (선택적)
    public void deleteImages(List<Long> imageIds) {
        for (Long imageId : imageIds) {
            Image image = imageRepository.findById(imageId)
                    .orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));

            // 물리적 파일 삭제
            deleteFile(image.getImageUrl());

            // DB에서 삭제
            imageRepository.delete(image);
        }
    }

    // 게시글의 모든 이미지 삭제
    public void deleteAllImagesByPost(Post post) {
        List<Image> images = imageRepository.findByPost(post);

        for (Image image : images) {
            deleteFile(image.getImageUrl());
            imageRepository.delete(image);
        }
    }

    // 게시글의 이미지 조회
    @Transactional(readOnly = true)
    public List<Image> getImagesByPost(Post post) {
        return imageRepository.findByPost(post);
    }

    @Transactional(readOnly = true)
    public List<Image> getImagesByPostId(Long postId) {
        return imageRepository.findByPost_PostId(postId);
    }

    // 파일 검증
    private void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("파일이 비어있습니다.");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("이미지 파일만 업로드 가능합니다.");
        }

        // 파일 크기 제한 (예: 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IllegalStateException("파일 크기는 5MB를 초과할 수 없습니다.");
        }
    }

    // 고유 파일명 생성
    private String generateFileName(String originalFilename) {
        String extension = "";
        int lastDot = originalFilename.lastIndexOf('.');
        if (lastDot > 0) {
            extension = originalFilename.substring(lastDot);
        }
        return UUID.randomUUID().toString() + extension;
    }

    // 파일 저장
    private String saveFile(MultipartFile file, String fileName) {
        try {
            Path uploadDir = Paths.get(uploadPath);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(fileName);
            file.transferTo(filePath.toFile());

            return "/uploads/" + fileName; // URL 경로
        } catch (IOException e) {
            throw new IllegalStateException("파일 업로드에 실패했습니다.", e);
        }
    }

    // 파일 삭제
    private void deleteFile(String imageUrl) {
        try {
            String fileName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
            Path filePath = Paths.get(uploadPath, fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // 로깅만 하고 예외는 던지지 않음 (DB 삭제는 진행)
            log.error("파일 삭제 실패: imageUrl = {}" , imageUrl);
        }
    }
}
