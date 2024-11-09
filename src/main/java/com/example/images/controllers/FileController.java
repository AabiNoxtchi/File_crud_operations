package com.example.images.controllers;

import com.example.images.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService service;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return service.upload(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id)  {
        return service.getById(id);
    }

    @GetMapping(value = "/info/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getInfoById(@PathVariable(value = "id") Long id) {
        return service.getInfoById(id);
    }
}
