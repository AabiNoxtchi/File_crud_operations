package com.example.images.services;

import com.example.images.models.Image;
import com.example.images.models.Base64ImageDTO;
import com.example.images.repositories.ImageRepository;
import com.example.images.utils.FileUtil;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository repo;

    public ResponseEntity<?> upload(MultipartFile img) {
        return upload(img, null);
    }

    public ResponseEntity<?> upload(MultipartFile img, @Nullable Long id) {
        if (!FileUtil.isValidImage(img)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("img file of type image is required");
        }

        Image foundImage = Objects.isNull(id) ?
                new Image() :
                repo.findById(id).orElse(new Image());

        foundImage.setImg(img);
        foundImage = repo.save(foundImage);
        return ResponseEntity.ok(foundImage.getId());
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<Image> byId = repo.findById(id);
        if (byId.isPresent()) {
            Image foundImage = byId.get();
            return ResponseEntity.ok()
                    .contentType(FileUtil.getMediaType(foundImage.getType()))
                    .body(foundImage.getImg());
        }
            return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getAll() {
        List<Image> images = repo.findAll();
        List<Base64ImageDTO> stringImages = images.stream().map(Base64ImageDTO::new).toList();
        return images.isEmpty() ?
                //ResponseEntity.notFound().build() :
                ResponseEntity.ok(Collections.EMPTY_LIST) :
                ResponseEntity.ok(stringImages);
    }
}
