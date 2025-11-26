package com.example.basicspringnewsfeed.image.service;

import com.example.basicspringnewsfeed.common.exception.CustomException;
import com.example.basicspringnewsfeed.common.exception.ErrorCode;
import com.example.basicspringnewsfeed.common.exception.FileUploadFailException;
import com.example.basicspringnewsfeed.image.entity.Image;
import com.example.basicspringnewsfeed.image.repository.ImageRepository;
import com.example.basicspringnewsfeed.post.entity.Post;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    // 이미지 업로드 (빈 파일 필터링 추가)
    public List<Image> uploadImages(Post post, List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();

        // null 체크
        if (files == null || files.isEmpty()) {
            return images;
        }

        for (MultipartFile file : files) {
            // 빈 파일 건너뛰기 (중요!)
            if (file.isEmpty()) {
                continue;
            }

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
                    .orElseThrow(() -> new CustomException(ErrorCode.IMAGE_NOT_FOUND));

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

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new CustomException(ErrorCode.ONLY_IMAGE_FILE);
        }

        // 파일 크기 제한 (예: 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new CustomException(ErrorCode.PAYLOAD_TOO_LARGE);
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
            // 저장 경로 준비
            // PATH를 사용하는 이유는 OS에 상관없이 경로 처리 가능
            // String보다 파일/디렉토리 작업이 편하다.
            Path uploadDir = Paths.get(uploadPath);

            // 디렉토리 확인 및 생성
            /**
             * createDirectory vs createDirectories
             *
             * createDirectory(): 부모 디렉토리가 없으면 에러
             * createDirectories(): 부모 디렉토리까지 모두 생성
             */
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            // 파일 저장 경로 설정
            // uploadDir = C:/uploads
            // fileName = "123456789_profile.jpg"
            // → targetLocation = C:/uploads/123456789_profile.jpg
            // resolve() 메서드: 경로를 안전하게 결합 , OS에 맞는 경로 구분자 자동 처리
            Path targetLocation = uploadDir.resolve(fileName);

            // 1) file.getInputStream()
            // MultipartFile의 데이터를 읽을 수 있는 스트림 반환
            // (메모리 또는 임시 저장소에 있는 파일 데이터)

            // 2) targetLocation
            // 저장할 위치: C:/uploads/123456789_profile.jpg

            // 3) StandardCopyOption.REPLACE_EXISTING
            // 같은 이름의 파일이 이미 있으면 덮어쓰기 >> UUID 적용으로 해결
            /**
             * **실제 동작:**
             * ```
             * 메모리/임시 저장소의 파일 데이터
             *     ↓ (읽기)
             * InputStream
             *     ↓ (복사)
             * C:/uploads/123456789_profile.jpg 에 저장
             */
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName; // URL 경로
        } catch (IOException e) {
            throw new FileUploadFailException(ErrorCode.INTERNAL_SERVER_ERROR, e);
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
