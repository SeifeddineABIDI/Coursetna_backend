package tn.esprit.pidev.services;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class ImageService {

    public String saveImage(MultipartFile imageFile, String imageName) {
        try {
            String uploadDir = "static/images"; // Relative path under resources/static
            String absolutePath = getAbsolutePath(uploadDir);

            // Create directory if it doesn't exist
            File directory = new File(absolutePath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Save image file
            Path filePath = Paths.get(absolutePath).resolve(StringUtils.cleanPath(imageName));
            Files.copy(imageFile.getInputStream(), filePath);

            // Return relative path to the saved image
            return Paths.get(uploadDir).resolve(imageName).toString();
        } catch (IOException ex) {
            throw new RuntimeException("Failed to save image", ex);
        }
    }

    private String getAbsolutePath(String relativePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(relativePath).getFile()).getAbsolutePath();
    }
}