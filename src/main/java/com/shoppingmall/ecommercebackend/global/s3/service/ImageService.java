package com.shoppingmall.ecommercebackend.global.s3.service;

import com.shoppingmall.ecommercebackend.global.s3.S3Uploader;
import com.shoppingmall.ecommercebackend.global.s3.dto.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Uploader s3Uploader;

    public ImageUploadResponse uploadImage(MultipartFile file, String dirName) {
        String imageUrl = s3Uploader.upload(file, dirName);
        return ImageUploadResponse.builder()
                .imageUrl(imageUrl)
                .build();
    }
}