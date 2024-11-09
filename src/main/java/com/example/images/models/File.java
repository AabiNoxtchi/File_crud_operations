package com.example.images.models;

import com.example.images.utils.FileUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String type;
    private int originalLength;
    private int compressedLength;

    @Lob
    private byte[] data;

    public File(MultipartFile file, int originalLength, byte[] compressedData) {
        this.name = FileUtil.getFileName(file);
        this.type = FileUtil.getFileType(file);
        this.originalLength = originalLength;
        this.data = compressedData;
    }
}
