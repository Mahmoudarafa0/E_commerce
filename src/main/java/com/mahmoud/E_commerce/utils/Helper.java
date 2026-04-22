package com.mahmoud.E_commerce.utils;

import com.mahmoud.E_commerce.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class Helper {

    public Long extractUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("User not authenticated");
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (customUserDetails == null) {
            throw new IllegalStateException("User not found");
        }
        return customUserDetails.getUserId();
    }


    public String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path uploadDir = Paths.get("uploads", "images")
                .toAbsolutePath()
                .normalize();

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(fileName);
        Files.write(filePath, image.getBytes());
        return fileName;
    }


    public void deleteImage(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            return;
        }
        fileName = Paths.get(fileName).getFileName().toString();
        Path filePath = Paths.get("uploads", "images")
                .toAbsolutePath()
                .normalize()
                .resolve(fileName);

        System.out.println("Deleting: " + filePath);
        System.out.println("Exists: " + Files.exists(filePath));
        if (Files.exists(filePath)) {
            Files.delete(filePath);
            System.out.println("Deleted successfully");
        } else {
            System.out.println("File not found");
        }
    }
}
