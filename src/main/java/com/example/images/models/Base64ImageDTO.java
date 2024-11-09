package com.example.images.models;

import com.example.images.utils.FileUtil;

public record Base64ImageDTO(Long id, String name, String type, String img) {
    public Base64ImageDTO(Image img) {
        this(img.getId(),
                img.getName(),
                img.getType(),
                FileUtil.toBase64(img.getImg()));
    }
}
