package com.example.picloud.services;


import com.example.picloud.entities.Cv;
import com.example.picloud.repository.ICvRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class CVServiceImp implements  ICvGestion{

    @Autowired
    private ICvRepo cvRepo;
    @Value("${upload.directory}")
    private String uploadDirectory;


    @Override
    @Transactional
    public Cv addCv(Cv cv, MultipartFile cheminFichier) throws IOException {
        if (cheminFichier == null || cheminFichier.isEmpty()) {
            throw new IllegalArgumentException("La photo est vide ou absente.");
        }
        String cheminUrl = saveFichier(cheminFichier);
        cv.setCheminFichier(cheminUrl);
        Cv savedCv = cvRepo.save(cv);


        return savedCv;
    }

    public String saveFichier(MultipartFile photo) throws IOException {
        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
        String photoUrl = uploadDirectory + "/" + fileName;

        try {
            Path uploadPath = Paths.get(uploadDirectory);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = photo.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                return photoUrl;
            }
        } catch (IOException ex) {
            throw new IOException("Impossible de télécharger le fichier : " + fileName, ex);
        }
    }


}
