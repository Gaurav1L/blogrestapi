package com.blogapp1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ImageController {

    import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

    @RestController
    @RequestMapping("/api/images")
    public class AddImageController {

        private static final String IMAGE_UPLOAD_DIR = "uploads/";

        // Handle image upload
        @PostMapping("/upload")
        public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
            if (image.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select an image to upload.");
            }

            try {
                // Create upload directory if it doesn't exist
                Path uploadPath = Paths.get(IMAGE_UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // Save the image to the upload directory
                String fileName = image.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(image.getInputStream(), filePath);

                return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully: " + fileName);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Could not upload the image: " + e.getMessage());
            }
        }
    }
}
