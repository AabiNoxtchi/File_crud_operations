package com.example.images.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public final class ImageUtil {

    private ImageUtil() {}

    public static boolean isValid(MultipartFile img) {
        return Objects.nonNull(img) &&
                !img.isEmpty() &&
                Objects.nonNull(img.getContentType()) &&
                img.getContentType().contains("image");
    }

    public static boolean isImageType(MultipartFile img) {
        return getImageType(img).contains("image");
    }

    public static String getImageType(MultipartFile img) {
        return img.getContentType();
    }

    public static String getImageName(MultipartFile img) {
        return img.getOriginalFilename();
    }

    public static byte[] getImgBytes(MultipartFile img) {
        try {
            return img.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("error occurred while processing image");
        }
    }
}
