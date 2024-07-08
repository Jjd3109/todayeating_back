package org.example.todayeating_back.service;

import lombok.RequiredArgsConstructor;
import org.example.todayeating_back.entity.Images;
import org.example.todayeating_back.repository.ImagesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImagesService {

    private final ImagesRepository imagesRepository;
    private static final String UPLOAD_DIR = "C:/Users/정종두/Desktop/테스트/";

    public Images saveImage(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload empty file");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(file.getInputStream(), filePath);

        Images imageEntity = Images.builder()
                .imagePath(filePath.toString())
                .build();
        return imagesRepository.save(imageEntity);
    }


}
