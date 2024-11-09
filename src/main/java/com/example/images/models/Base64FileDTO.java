package com.example.images.models;

import com.example.images.utils.FileUtil;

public record Base64FileDTO(Long id, String name, String type, String data,
                            int originalLength, int compressedLength) {
    public Base64FileDTO(File file) {
        this(file.getId(),
                file.getName(),
                file.getType(),
                FileUtil.toBase64(file.getData()),
                file.getOriginalLength(), file.getCompressedLength());
    }
}
