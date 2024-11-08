package com.example.images.models;

import java.util.Base64;

public record String64ImageDTO(Long id, String name, String type, String img) {
    public String64ImageDTO(Image img) {
        this(img.getId(),
                img.getName(),
                img.getType(),
                Base64.getEncoder().encodeToString(img.getImg()));
    }
}
