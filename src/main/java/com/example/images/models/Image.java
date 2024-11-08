package com.example.images.models;

import com.example.images.utils.ImageUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob
    private byte[] img;


    public void setImg(MultipartFile img) {
        this.name = ImageUtil.getImageName(img);
        this.type = ImageUtil.getImageType(img);
        this.img = ImageUtil.getImgBytes(img);
    }
}