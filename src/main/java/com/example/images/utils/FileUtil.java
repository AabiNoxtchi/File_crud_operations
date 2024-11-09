package com.example.images.utils;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class FileUtil {

    private static final Map<String, MediaType> COMMON_MEDIA_TYPES = new LinkedHashMap<>() {{
        put("application/pdf", MediaType.APPLICATION_PDF); // PDF
        put("text/plain", MediaType.TEXT_PLAIN); // Plain Text
        put("text/csv", MediaType.valueOf("text/csv")); // CSV
        put("application/json", MediaType.APPLICATION_JSON); // JSON
        put("application/xml", MediaType.APPLICATION_XML); // XML
        put("application/zip", MediaType.valueOf("application/zip")); // ZIP
        put("application/msword", MediaType.valueOf("application/msword")); // Word (DOC)
        put("application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                MediaType.valueOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document")); // Word (DOCX)
        put("application/vnd.ms-excel", MediaType.valueOf("application/vnd.ms-excel")); // Excel (XLS)
        put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                MediaType.valueOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")); // Excel (XLSX)
        put("application/vnd.ms-powerpoint", MediaType.valueOf("application/vnd.ms-powerpoint")); // PowerPoint (PPT)
        put("application/vnd.openxmlformats-officedocument.presentationml.presentation",
                MediaType.valueOf("application/vnd.openxmlformats-officedocument.presentationml.presentation")); // PowerPoint (PPTX)
        put("audio/mpeg", MediaType.valueOf("audio/mpeg")); // MP3 Audio
        put("audio/wav", MediaType.valueOf("audio/wav")); // WAV Audio
        put("video/mp4", MediaType.valueOf("video/mp4")); // MP4
        put("image/jpeg", MediaType.IMAGE_JPEG); // JPEG Image
        put("image/png", MediaType.IMAGE_PNG); // PNG Image
        put("image/gif", MediaType.IMAGE_GIF); // GIF Image
        put("image/svg+xml", MediaType.valueOf("image/svg+xml")); // SVG Image
    }};

    private FileUtil() {}

    public static boolean isValidImage(MultipartFile img) {
        return isValid(img) && img.getContentType().contains("image");
    }

    public static boolean isValid(MultipartFile file) {
        return Objects.nonNull(file) &&
                !file.isEmpty() &&
                Objects.nonNull(file.getContentType()) &&
                Objects.nonNull(COMMON_MEDIA_TYPES.get(file.getContentType()));
    }

    public static String getFileType(MultipartFile img) {
        return img.getContentType();
    }

    public static String getFileName(MultipartFile img) {
        return img.getOriginalFilename();
    }

    public static byte[] getFileBytes(MultipartFile img) {
        try {
            return img.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("error occurred while processing image");
        }
    }

    public static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static MediaType getMediaType(String type) {
        return COMMON_MEDIA_TYPES.getOrDefault(type, MediaType.APPLICATION_OCTET_STREAM);
    }
}
