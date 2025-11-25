package com.example.basicspringnewsfeed.image.dto;

import com.example.basicspringnewsfeed.image.entity.Image;
import lombok.Getter;

@Getter
public class ImageDto {

    private Long imageId;
    private String fileName;
    private String imageUrl;

    public ImageDto(Image image) {
        this.imageId = image.getImageId();
        this.fileName = image.getFileName();
        this.imageUrl = image.getImageUrl();
    }
}
