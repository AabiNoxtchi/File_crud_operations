package com.example.images.controllers;

import com.example.images.servicees.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService service;

    @PostMapping
    public ResponseEntity<?> uploadFile(MultipartFile img) {

        return service.upload(img);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> uploadFile(MultipartFile img,
                                        @PathVariable("id") Long id) {

        return service.upload(img, id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImageById(@PathVariable(value = "id") Long id) {
        return service.getById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getImages() {
        return service.getAll();
    }
}
