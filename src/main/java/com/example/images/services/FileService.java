package com.example.images.services;

import com.example.images.models.Base64FileDTO;
import com.example.images.models.File;
import com.example.images.repositories.FileRepository;
import com.example.images.utils.CompressorUtil;
import com.example.images.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository repo;

    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        if (!FileUtil.isValid(file)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("valid file of supported type is required");
        }

        byte[] originalData = FileUtil.getFileBytes(file);
        byte[] compressedData = CompressorUtil.compressFile(originalData);
        File fileToSave = new File(file, originalData.length, compressedData);

        repo.save(fileToSave);
        return ResponseEntity.ok(fileToSave.getId());
    }

    public ResponseEntity<?> getById(Long id) {
        Optional<File> fileOptional = repo.findById(id);
        if(fileOptional.isPresent()) {
            try {
                File file = fileOptional.get();
                return ResponseEntity.status(HttpStatus.OK)
                                .contentType(FileUtil.getMediaType(file.getType()))
                                .body(CompressorUtil.decompressFile(file.getData()));
            } catch (DataFormatException | IOException exception) {
                throw new RuntimeException("error occurred while processing file");
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> getInfoById(Long id) {
        Optional<File> fileOptional = repo.findById(id);
        if(fileOptional.isPresent()) {
            try {
                File file = fileOptional.get();
                file.setData(CompressorUtil.decompressFile(file.getData()));
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new Base64FileDTO(file));

            } catch (DataFormatException | IOException exception) {
                throw new RuntimeException("error occurred while processing file");
            }
        }

        return ResponseEntity.notFound().build();
    }
}
